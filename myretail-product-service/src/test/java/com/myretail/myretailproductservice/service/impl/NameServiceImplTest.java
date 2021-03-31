package com.myretail.myretailproductservice.service.impl;

import com.myretail.myretailproductservice.exception.ApiException;
import com.myretail.myretailproductservice.web.model.Name;
import com.myretail.myretailproductservice.web.model.Price;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class NameServiceImplTest {

    private String nameByIdUrl;
    private ResponseEntity<Name> testResponseEntity;

    @Mock
    private RestTemplate mockApiRestTemplate;
    private Map<String,String> testParamMap;

    @InjectMocks
    private NameServiceImpl nameService;

    private Name testName;


    @Before
    public void setUp() throws Exception {
        testParamMap = new HashMap<>();
        testParamMap.put("id" ,"1");

        ReflectionTestUtils.setField(nameService , "nameByIdUrl" , nameByIdUrl);
        testName = Name.builder().id("1").name("test product").build();
        testResponseEntity = ResponseEntity.of(Optional.of(testName));

    }

    @Test
    public void shouldGetNameByIdWhenNameServiceResponseOk() {
        testResponseEntity = ResponseEntity.ok(testName);
        when(mockApiRestTemplate.getForEntity(nameByIdUrl, Name.class, testParamMap)).thenReturn(testResponseEntity);
        Name actualName = nameService.getNameById("1");
        Assert.assertEquals(testName , actualName);
        verify(mockApiRestTemplate,times(1)).getForEntity(nameByIdUrl, Name.class, testParamMap);
        verifyNoMoreInteractions(mockApiRestTemplate);
    }

    @Test(expected = ApiException.class)
    public void shouldThrowApiExceptionWhenGetNameServiceResponseNotOk() {
        when(mockApiRestTemplate.getForEntity(nameByIdUrl, Name.class, testParamMap)).thenThrow(HttpClientErrorException.class);
        Name actualName = nameService.getNameById("1");
        verify(mockApiRestTemplate,times(1)).getForEntity(nameByIdUrl, Name.class, testParamMap);
        verifyNoMoreInteractions(mockApiRestTemplate);
    }
}