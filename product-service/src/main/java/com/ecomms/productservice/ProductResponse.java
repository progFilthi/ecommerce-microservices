package com.ecomms.productservice;

import java.io.Serializable;
import java.math.BigDecimal;

//Serializable because Redis stores cached objects as Bytes.
public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int stock
) implements Serializable {
}
