package com.luizalabs.challenge.service;

import com.luizalabs.challenge.model.entity.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    Product getProductById(long id);

    Product updateProduct(Product product);

    void deleteProduct(Product product);


    List<Product> filterProduct(Product productFilter);
}
