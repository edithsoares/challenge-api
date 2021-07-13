package com.luizalabs.challenge.controller;

import com.luizalabs.challenge.model.entity.Product;
import com.luizalabs.challenge.model.entity.Wishlist;
import com.luizalabs.challenge.service.ProductService;
import com.luizalabs.challenge.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("challenge-api.luizalabs.com/api")
@RequiredArgsConstructor
public class WishlistController {


    private final WishlistService service;
    private final ProductService serviceProduct;



    @PostMapping("/wishlist")
    public ResponseEntity<?> createWishlist(@RequestBody Wishlist wishlist){
        try {
            return new ResponseEntity<>(service.createWishlist(wishlist), HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("wishlist/{id}/{email}")
    public ResponseEntity<?> addingWishlist(@PathVariable String email, @PathVariable long idProduct){
        Wishlist resultEmail = service.findByClientEmail(email);
        try {
            if (resultEmail == null){
                return new ResponseEntity<>("User não encontrado", HttpStatus.NOT_FOUND);
            }else {
                Product resultProd = serviceProduct.getProductById(idProduct);
                if (resultProd == null){
                    return new ResponseEntity<>("produto não cadastrado", HttpStatus.NOT_FOUND);
                }

                List<Product> productResult = resultEmail.getProducts();
                for (Product prod : productResult) {
                    if (prod.getId().equals(idProduct)) {
                        return new ResponseEntity<>("produto ja adicionado na lista", HttpStatus.CONFLICT);
                    }
                }

                List<Product> listProduct = resultEmail.getProducts();
                listProduct.add(resultProd);
                resultEmail.setProducts(listProduct);

                return new ResponseEntity<>("Ok", HttpStatus.CREATED);
            }
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity getWishlist( @PathVariable("id") Long id ) {
        return service.getById(id)
                .map( product -> new ResponseEntity(product, HttpStatus.OK) )
                .orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND) );
    }

    @GetMapping("/wishlist/{email}/{idProduct}")
    public ResponseEntity<?> getProductById(@PathVariable String email, @PathVariable long idProduct) {
        try {
            Wishlist cpfClient = service.findByClientEmail(email);
            if (cpfClient == null) {
                return new ResponseEntity<>("Cpf not found email: ",
                        HttpStatus.NOT_FOUND);
            } else {
                List<Product> productResult = cpfClient.getProducts();
                for (Product prod : productResult) {
                    if (prod.getId().equals(idProduct)) {
                        return new ResponseEntity<>(prod, HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<>("Cpf not found cpf: ",
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>( "Request Errors",
                    HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/wishlist/{id}")
    public ResponseEntity<List<?>> listAllProducts(@PathVariable Long id) {
        try {
            Wishlist resultId = service.findById(id);
            if (resultId != null) {
                List<Product> listProducts = resultId.getProducts();
                return new ResponseEntity<>(listProducts, HttpStatus.OK);
            } else {
                return new ResponseEntity("Cpf not found cpf: ", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity("Request Errors", HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteWishlist( @PathVariable("id") Long id ) {
        return service.getById(id).map( entidade -> {
            service.delete(entidade);
            return new ResponseEntity<>( HttpStatus.NO_CONTENT );
        }).orElseGet( () ->
                new ResponseEntity<>("Lancamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
    }
}
