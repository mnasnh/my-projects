package com.myretail.myretailproductservice.service.impl;

import com.myretail.myretailproductservice.exception.ApiException;
import com.myretail.myretailproductservice.service.NameService;
import com.myretail.myretailproductservice.service.PriceService;
import com.myretail.myretailproductservice.web.model.Name;
import com.myretail.myretailproductservice.web.model.Price;
import com.myretail.myretailproductservice.web.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductAggregatorServiceImplTest {

    @Mock
    private PriceService priceService;

    @Mock
    private NameService nameService;

    @InjectMocks
    private ProductAggregatorServiceImpl productAggregatorService;

    private Price testPrice;
    private Name testName;
    private Product testProduct;

    @Before
    public void setUp() throws Exception {
        testPrice = Price.builder().currencyCode("USD").value("10.35").build();
        testName = Name.builder().id("1").name("test product").build();
        testProduct = Product.builder().id("1").name("test product").price(testPrice).build();
    }

    @Test
    public void shouldAggregateProductDataWhenServiceResponseOk() {

        when(priceService.getCurrentPriceById("1")).thenReturn(testPrice);
        when(nameService.getNameById("1")).thenReturn(testName);
        Product actualProduct = productAggregatorService.aggregateProductData("1");
        Assert.assertEquals(testProduct , actualProduct);
        verify(priceService , times(1)).getCurrentPriceById("1");
        verify(nameService , times(1)).getNameById("1");
        verifyNoMoreInteractions(priceService , nameService);

    }

    @Test(expected = ApiException.class)
    public void shouldThrowExceptionWhenServiceResponse4xError() {

        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST , null , "error" );
        when(priceService.getCurrentPriceById("1")).thenThrow(apiException);
        Product actualProduct = productAggregatorService.aggregateProductData("1");

    }

    @Test
    public void shouldSetEmptyPriceWhenServiceResponseAnyOtherError() {

        testPrice = Price.builder().build();
        testProduct.setPrice(testPrice);
        ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR , null , "error" );
        when(priceService.getCurrentPriceById("1")).thenThrow(apiException);
        when(nameService.getNameById("1")).thenReturn(testName);
        Product actualProduct = productAggregatorService.aggregateProductData("1");
        Assert.assertEquals(testProduct , actualProduct);

    }
}