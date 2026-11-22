package com.ecomms.productservice;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductResponse create(ProductRequest request){
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    public List<ProductResponse> findAll(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    //First call comes from Postgres, later calls come from Redis until TTL or eviction.
    @Cacheable(value = "products", key = "#id")
    public ProductResponse findById(Long id){
        return mapper.toResponse(getOrThrow(id));
    }

    //Evict so the next read fetches fresh data.
    @CacheEvict(value = "products", key = "#id")
    public ProductResponse update(Long id, ProductRequest request){
        var product = getOrThrow(id);
        mapper.updateEntity(product, request);
        return mapper.toResponse(repository.save(product));
    }

    @CacheEvict(value = "products", key = "#id")
    public void delete(Long id){
        getOrThrow(id);
        repository.deleteById(id);
    }

    /*
    * Called by the order-service.
    * Stock changed so the cached copy is stale: evict it.
    * */
    @CacheEvict(value = "products", key = "#id")
    public void reserveStock(Long id, int quantity){

        if(repository.decrementStock(id, quantity) == 0){
            //Zero rows updated: tell apart "No such product" from "not enough stock".
            throw repository.existsById(id)
                    ? new InsufficientStockException(id)
                    : new ProductNotFoundException(id);
        }
    }


    private Product getOrThrow(Long id){
        return repository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
    }

}
