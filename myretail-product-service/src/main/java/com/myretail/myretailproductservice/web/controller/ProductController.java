package com.myretail.myretailproductservice.web.controller;

import com.myretail.myretailproductservice.web.model.Product;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    @GetMapping(value = "/test")
    public String custom() {
        return "Hello World";
    }

    @GetMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get product details for product Id")
    public ResponseEntity<Product> getProduct(@PathVariable final String id) {

        return ResponseEntity.ok(Product.builder().id(id).build());
    }


}
