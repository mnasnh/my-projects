package com.myretail.myretailproductservice.service;

import com.myretail.myretailproductservice.web.model.Name;

public interface NameService {

    /**
     * Get Product Name by  ID from Name Service
     * @param id
     * @return Name
     */
    Name getNameById(String id);
}
