package com.myretail.myretailproductservice.service.impl;

import com.myretail.myretailproductservice.exception.ApiException;
import com.myretail.myretailproductservice.service.NameService;
import com.myretail.myretailproductservice.web.model.Name;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class NameServiceImpl implements NameService {

    @Value("${api.product-name-by-id.url}")
    private String nameByIdUrl;

    @Autowired
    @Qualifier(value = "mockApiRestTemplate")
    private RestTemplate mockApiRestTemplate;


    /**
     * Get Product Name by  ID from Name Service
     *
     * @param id
     * @return Name
     */
    @Override
    public Name getNameById(String id) {
        ResponseEntity<Name> nameResponseEntity = null;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", id);
            nameResponseEntity = mockApiRestTemplate.getForEntity(nameByIdUrl, Name.class, params);
            if (nameResponseEntity!=null && nameResponseEntity.getStatusCode() != null && nameResponseEntity.getStatusCode().is2xxSuccessful()) {
                return nameResponseEntity.getBody();
            }
        } catch (HttpClientErrorException e) {
            log.error("Error occurred while fetching product name from name service", e);
            if (nameResponseEntity != null) {
                throw new ApiException(nameResponseEntity.getStatusCode(), e.getCause(), e.getMessage());
            } else {
                throw new ApiException(e.getStatusCode(), e.getCause(), e.getMessage());
            }
        }
        return null;
    }
}
