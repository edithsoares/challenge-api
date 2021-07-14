package com.luizalabs.challenge.service.impl;

import com.luizalabs.challenge.model.entity.Product;
import com.luizalabs.challenge.model.entity.Wishlist;
import com.luizalabs.challenge.model.repository.ProductRepository;
import com.luizalabs.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;


    public Product saveProduct(Product product){
        Objects.requireNonNull(product);
        return repository.save(product);
    }

    public Product getProductById(long id){
        return repository.findById(id);
    }

    public Product updateProduct(Product product){
        return repository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        Objects.requireNonNull(product.getId());
        repository.delete(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    public List<Product> AllProduct(){
        return repository.findAll();
    }

    @Override
    public List<Product> filterProduct(Product productFilter) {

        Example example = Example.of(productFilter, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        return repository.findAll(example);
    }

    public void deleteProduct(long id) {
        repository.deleteById(id);
    }

    public static void reviewScore(Product product) {
        int total = 0;
        total += 1;
        product.setReviewScore(total);
    }
}
