package com.myretail.myretailproductservice.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Price {
    private String value;
    private String currencyCode;
}
