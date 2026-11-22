package com.ecomms.productservice;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product %d not found".formatted(id));
    }
}
