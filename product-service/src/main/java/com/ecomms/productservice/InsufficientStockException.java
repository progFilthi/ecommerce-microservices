package com.ecomms.productservice;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long id) {
        super("Not enough stock for product %d".formatted(id));
    }
}
