package com.myretail.myretailproductservice.service;

import com.myretail.myretailproductservice.web.model.Price;

public interface PriceService {
    /*
    Get current price for product id from price service
     */
    Price getCurrentPriceById(String id);

    /*
    Update current price of a product
     */
    Price updateCurrentPriceById(String id, Price price);
}
