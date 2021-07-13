package com.luizalabs.challenge.controller;


import com.luizalabs.challenge.model.entity.Product;
import com.luizalabs.challenge.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    // Documentação / Refatoração para melhorias

    @Autowired
    private ProductServiceImpl service;

    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        try {
            return new ResponseEntity<>(service.saveProduct(product), HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/product/id/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, long id){
        Optional<Product> resultId = Optional.ofNullable(service.getProductById(id));
        try {
            if (resultId.isPresent()){
                product.setId(resultId.get().getId());
                return new ResponseEntity<>(service.saveProduct(product), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/id/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id){
        Product resultId = service.getProductById(id);
        try {
            if (resultId != null){
                service.deleteProduct(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
