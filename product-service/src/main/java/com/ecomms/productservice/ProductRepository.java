package com.ecomms.productservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    /*
    * One ATOMIC UPDATE: - only decrements if enough stock exists.
    * Returns 1 on success, 0 if not enough stock or missing product.
    * No race condition.
    * */
    @Modifying
    @Transactional
    @Query("update Product p set p.stock = p.stock - :quantity where p.id = :id and p.stock >= :quantity")
    int decrementStock(Long id, int quantity);

    List<Product> id(Long id);
}
