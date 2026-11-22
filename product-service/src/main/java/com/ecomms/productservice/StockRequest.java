package com.ecomms.productservice;

import jakarta.validation.constraints.Positive;

public record StockRequest(

        @Positive
        int quantity
) {
}
