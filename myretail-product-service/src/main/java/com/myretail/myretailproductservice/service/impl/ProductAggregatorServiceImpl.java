package com.myretail.myretailproductservice.service.impl;

import com.myretail.myretailproductservice.exception.ApiException;
import com.myretail.myretailproductservice.service.NameService;
import com.myretail.myretailproductservice.service.PriceService;
import com.myretail.myretailproductservice.service.ProductAggregatorService;
import com.myretail.myretailproductservice.web.model.Name;
import com.myretail.myretailproductservice.web.model.Price;
import com.myretail.myretailproductservice.web.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductAggregatorServiceImpl implements ProductAggregatorService {

    @Autowired
    private PriceService priceService;

    @Autowired
    private NameService nameService;

    /**
     * aggregates price information from price service and product name from name service
     * and builds product model
     *
     * @param productId
     * @return Product
     */
    @Override
    public Product aggregateProductData(String productId) {
        Price price;
        log.info("Aggregating product price and name");
        try {
            price = priceService.getCurrentPriceById(productId);
        } catch (ApiException e) {
            if (e.getStatus().is4xxClientError()) {
                throw e;
            } else {
                log.error("Could not aggregate product data:", e);
                price = Price.builder().build();
            }
        }
        Name name;
        try {
            name = nameService.getNameById(productId);

        } catch (ApiException e) {
            if (e.getStatus().is4xxClientError()) {
                throw e;
            } else {
                log.error("Could not aggregate product data:", e);
                name = Name.builder().name("").build();

            }
        }
      return Product.builder().id(productId).price(price).name(name.getName()).build();


    }
}
