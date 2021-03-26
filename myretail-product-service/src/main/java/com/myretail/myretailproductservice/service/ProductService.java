package com.myretail.myretailproductservice.service;

import com.myretail.myretailproductservice.web.model.Product;

public interface ProductService {

    Product getProduct(String id);
    Product updateProduct(String id , Product product);
}
