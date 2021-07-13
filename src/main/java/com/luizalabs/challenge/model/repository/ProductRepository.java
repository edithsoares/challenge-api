package com.luizalabs.challenge.model.repository;

import com.luizalabs.challenge.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findById(long id);

}
