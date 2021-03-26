package com.myretail.myretailproductservice.service;

import com.myretail.myretailproductservice.web.model.Product;

public interface ProductAggregatorService {
    /**
     * aggregates price information from price service and product name from name service
     * and builds product model
     * @param productId
     * @return Product
     */
    Product aggregateProductData(String productId);
}
