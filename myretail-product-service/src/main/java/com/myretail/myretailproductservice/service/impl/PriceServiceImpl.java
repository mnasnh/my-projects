package com.myretail.myretailproductservice.service.impl;

import com.myretail.myretailproductservice.exception.ApiException;
import com.myretail.myretailproductservice.service.PriceService;
import com.myretail.myretailproductservice.web.model.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PriceServiceImpl implements PriceService {

    @Value("${api.price-by-id.url}")
    private String priceByIdUrl;

    @Autowired
    @Qualifier(value = "mockApiRestTemplate")
    private RestTemplate mockApiRestTemplate;

    @Override

    public Price getCurrentPriceById(String id) {
        ResponseEntity<Price> priceResponseEntity = null;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", id);
            priceResponseEntity = mockApiRestTemplate.getForEntity(priceByIdUrl, Price.class, params);
            if (priceResponseEntity != null && priceResponseEntity.getStatusCode() != null && priceResponseEntity.getStatusCode().is2xxSuccessful()) {
                return priceResponseEntity.getBody();
            }
        } catch (HttpClientErrorException e) {
            log.error("Error occurred while fetching current price from price service", e);
            if (priceResponseEntity != null) {
                throw new ApiException(priceResponseEntity.getStatusCode(), e.getCause(), e.getMessage());
            } else {
                throw new ApiException(e.getStatusCode(), e.getCause(), e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Price updateCurrentPriceById(String id, Price price) {
        RequestEntity<Price> requestEntity;
        ResponseEntity<Price> priceResponseEntity = null;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", id);
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(priceByIdUrl);
            requestEntity = RequestEntity.put(builder.buildAndExpand(params).toUri()).contentType(MediaType.APPLICATION_JSON).body(price);

            priceResponseEntity = mockApiRestTemplate.exchange(requestEntity, Price.class);
            if (priceResponseEntity != null && priceResponseEntity.getStatusCode() != null && priceResponseEntity.getStatusCode().is2xxSuccessful()) {
                return priceResponseEntity.getBody();
            }
        } catch (HttpClientErrorException e) {
            log.error("Error occurred while updating current price for product id: {}", id, e);
            if (priceResponseEntity != null) {
                throw new ApiException(priceResponseEntity.getStatusCode(), e.getCause(), e.getMessage());
            } else {
                throw new ApiException(e.getStatusCode(), e.getCause(), e.getMessage());
            }
        }
        return null;
    }
}
