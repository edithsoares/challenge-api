package com.luizalabs.challenge.controller;


import com.luizalabs.challenge.model.entity.Product;
import com.luizalabs.challenge.model.entity.User;
import com.luizalabs.challenge.payload.Response;
import com.luizalabs.challenge.service.impl.ProductServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl service;


    @ApiOperation(value = "Save a new Product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the registered product", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        try {
            return new ResponseEntity<>(service.saveProduct(product), HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity<>(new Response(false, "Request errors"), HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Update product")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Returns the list of products", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 404, message = "Product not found", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product){
        try {
            Optional<Product> resultId = service.findById(id);
            if (resultId.isPresent()){
                product.setId(resultId.get().getId());
                return new ResponseEntity<>(service.updateProduct(product), HttpStatus.OK);
            }
            return new ResponseEntity<>(new Response(false, "Product not found by id" + id),
                    HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(new Response(false, "Request errors"), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Delete products")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Returns the id deleted", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 404, message = "Id Product not found", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id){
        Product resultId = service.getProductById(id);
        try {
            if (resultId != null){
                service.deleteProduct(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(new Response(false, "Product not found by id" + id),
                    HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(new Response(false, "Request errors"), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Search all products")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Returns the list of products", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 404, message = "List products not found", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @GetMapping
    public ResponseEntity<?> listAllProducts(){
        try {
            List<Product> products = service.AllProduct();
            if (products != null){
                return new ResponseEntity<>(products, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new Response(false, "Product not found"),
                        HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new Response(false, "Request errors"), HttpStatus.BAD_REQUEST);
        }
    }
}
