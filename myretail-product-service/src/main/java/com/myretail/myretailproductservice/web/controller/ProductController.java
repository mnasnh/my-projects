package com.myretail.myretailproductservice.web.controller;

import com.myretail.myretailproductservice.service.PriceService;
import com.myretail.myretailproductservice.service.ProductAggregatorService;
import com.myretail.myretailproductservice.web.model.Product;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    @Autowired
    private ProductAggregatorService productAggregatorService;

    @Autowired
    private PriceService priceService;

    @GetMapping(value = "/test")
    public String custom() {
        return "Hello World";
    }

    @GetMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get product details for product Id")
    public ResponseEntity<Product> getProduct(@PathVariable final String id) {
        Product product = productAggregatorService.aggregateProductData(id);
        return ResponseEntity.ok(product);
    }
    @PutMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Updates product's price details for product Id")
    public ResponseEntity<Product> updateProduct(@PathVariable final String id , @RequestBody final Product product) {
        priceService.updateCurrentPriceById(id , product.getPrice());
        Product upDatedProduct = productAggregatorService.aggregateProductData(id);
        return ResponseEntity.ok(upDatedProduct);
    }

}
