package com.myretail.myretailproductservice.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private String id;
    private String name;
    private Price price;
}
