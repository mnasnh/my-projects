package com.myretail.myretailproductservice.service.impl;

import com.myretail.myretailproductservice.exception.ApiException;
import com.myretail.myretailproductservice.web.model.Price;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PriceServiceImplTest {

    private String priceByIdUrl = "http://localhost:3000/currentprice/{id}";
    private ResponseEntity<Price> testResponseEntity;
    private Price testPrice;

    @Mock
    private RestTemplate mockApiRestTemplate;

    @InjectMocks
    private PriceServiceImpl priceService;

    private Map<String,String> testParamMap;

    @Before
    public void setUp() throws Exception {
        testParamMap = new HashMap<>();
        testParamMap.put("id" ,"1");

        ReflectionTestUtils.setField(priceService , "priceByIdUrl" , priceByIdUrl);
        testPrice = Price.builder().currencyCode("USD").value("10.35").build();
        testResponseEntity = ResponseEntity.of(Optional.of(testPrice));

    }

    @Test
    public void shouldGetCurrentPriceByIdWhenPriceServiceResponseOk() {
        testResponseEntity = ResponseEntity.ok(testPrice);
        when(mockApiRestTemplate.getForEntity(priceByIdUrl, Price.class, testParamMap)).thenReturn(testResponseEntity);
        Price actualPrice = priceService.getCurrentPriceById("1");
        Assert.assertEquals(testPrice , actualPrice);
        verify(mockApiRestTemplate,times(1)).getForEntity(priceByIdUrl, Price.class, testParamMap);
        verifyNoMoreInteractions(mockApiRestTemplate);
    }

    @Test
    public void shouldUpdateCurrentPriceById() {
        testResponseEntity = ResponseEntity.ok(testPrice);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(priceByIdUrl);
        RequestEntity<Price> requestEntity = RequestEntity.put(builder.buildAndExpand(testParamMap).toUri()).contentType(MediaType.APPLICATION_JSON).body(testPrice);
        when(mockApiRestTemplate.exchange(requestEntity, Price.class)).thenReturn(testResponseEntity);
        Price updatedPrice = priceService.updateCurrentPriceById("1" , testPrice);
        Assert.assertEquals(testPrice , updatedPrice);
        verify(mockApiRestTemplate,times(1)).exchange(requestEntity, Price.class);
        verifyNoMoreInteractions(mockApiRestTemplate);
    }

    @Test(expected = ApiException.class)
    public void shouldThrowApiExceptionWhenGetPriceServiceResponseNotOk() {
        when(mockApiRestTemplate.getForEntity(priceByIdUrl, Price.class, testParamMap)).thenThrow(HttpClientErrorException.class);
        Price actualPrice = priceService.getCurrentPriceById("1");
        verify(mockApiRestTemplate,times(1)).getForEntity(priceByIdUrl, Price.class, testParamMap);
        verifyNoMoreInteractions(mockApiRestTemplate);
    }

    @Test(expected = ApiException.class)
    public void shouldThrowApiExceptionWhenUpdatePriceServiceResponseNotOk() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(priceByIdUrl);
        RequestEntity<Price> requestEntity = RequestEntity.put(builder.buildAndExpand(testParamMap).toUri()).contentType(MediaType.APPLICATION_JSON).body(testPrice);
        when(mockApiRestTemplate.exchange(requestEntity, Price.class)).thenThrow(HttpClientErrorException.class);
        Price updatedPrice = priceService.updateCurrentPriceById("1" , testPrice);
        verify(mockApiRestTemplate,times(1)).exchange(requestEntity, Price.class);
        verifyNoMoreInteractions(mockApiRestTemplate);
    }
}