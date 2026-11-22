package com.ecomms.productservice;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    //request - new entity (used when creating).
    public Product toEntity(ProductRequest request){

        return Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .build();

    }

    //Entity -> response (What the client sees).
    public ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

    //Copies request values onto an existing entity (used when updating).
    public void updateEntity(Product product, ProductRequest request){
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
    }


}
