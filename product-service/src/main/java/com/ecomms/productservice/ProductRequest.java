package com.ecomms.productservice;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank
        String name,

        @Size(max = 500)
        String description,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal price,

        @PositiveOrZero
        int stock
) {
}
