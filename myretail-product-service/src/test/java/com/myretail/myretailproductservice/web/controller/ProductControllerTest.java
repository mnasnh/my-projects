package com.myretail.myretailproductservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.myretailproductservice.service.PriceService;
import com.myretail.myretailproductservice.service.ProductAggregatorService;
import com.myretail.myretailproductservice.web.model.Name;
import com.myretail.myretailproductservice.web.model.Price;
import com.myretail.myretailproductservice.web.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductAggregatorService productAggregatorService;
    @MockBean
    private PriceService priceService;

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
    public void shouldGetProduct() throws Exception {

        when(productAggregatorService.aggregateProductData("1")).thenReturn(testProduct);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", "1").contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        testPrice.setValue("100");
        testProduct.setPrice(testPrice);
        when(priceService.updateCurrentPriceById("1" , testProduct.getPrice())).thenReturn(testPrice);
        when(productAggregatorService.aggregateProductData("1")).thenReturn(testProduct);
        ObjectMapper objectMapper = new ObjectMapper();
        String priceJson = objectMapper.writeValueAsString(testPrice);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}" , "1").
                content(priceJson).contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1")).andExpect(MockMvcResultMatchers.
                jsonPath("$.price.value").value("100"));
    }
}