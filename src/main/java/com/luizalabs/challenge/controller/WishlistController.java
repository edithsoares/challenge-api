package com.luizalabs.challenge.controller;

import com.luizalabs.challenge.model.entity.Product;
import com.luizalabs.challenge.model.entity.Wishlist;
import com.luizalabs.challenge.payload.Response;
import com.luizalabs.challenge.service.ProductService;
import com.luizalabs.challenge.service.WishlistService;
import com.luizalabs.challenge.service.impl.WishlistServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {


    private final WishlistService wishlistService;
    private final ProductService serviceProduct;


    @ApiOperation(value = "Save new Wishlist")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the registered Wishlist", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Wishlist wishlist){
        try {
            return new ResponseEntity<>(wishlistService.saveWishlist(wishlist), HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "add new product in Wishlist")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the registered wishlist", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @PostMapping("/{email}/{idProduct}")
    public ResponseEntity<?> addProductInWishlistClient(@PathVariable String email, @PathVariable long idProduct) {
        Wishlist checkEmailUser = wishlistService.findByUserEmail(email);
        if (checkEmailUser == null) {
            return new ResponseEntity<>(
                    new Response(false, "Cpf not found cpf: " + email),
                    HttpStatus.NOT_FOUND);
        } else {
            Product productResult = serviceProduct.getProductById(idProduct);
            if (productResult == null) {
                return new ResponseEntity<>(
                        new Response(false, "Product not found id: " + idProduct),
                        HttpStatus.NOT_FOUND);
            }
            List<Product> listProduct = checkEmailUser.getProducts();
            listProduct.add(productResult);
            checkEmailUser.setProducts(listProduct);

            wishlistService.sumTotal(checkEmailUser);

            if (checkEmailUser.getProducts().size() <= 20) {
                Wishlist newListProduct = wishlistService.saveWishlist(checkEmailUser);
                return new ResponseEntity<>(newListProduct, HttpStatus.OK);
            }
            return new ResponseEntity<>(new Response(false, "Request Errors"),
                    HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Check if product exists")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the product", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 404, message = "Passenger not found", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @GetMapping("/{email}/{idProduct}")
    public ResponseEntity<?> checkExistProduct(@PathVariable String email, @PathVariable long idProduct) {
        try {
            Wishlist resultEmail = wishlistService.findByUserEmail(email);
            if (resultEmail == null) {
                return new ResponseEntity<>(
                        new Response(false, "Cpf not found cpf: " + resultEmail),
                        HttpStatus.NOT_FOUND);
            } else {
                List<Product> productResult = resultEmail.getProducts();
                for (Product prod : productResult) {
                    if (prod.getId().equals(idProduct)) {
                        return new ResponseEntity<>(prod, HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<>(
                    new Response(false, "Cpf not found cpf: " + resultEmail),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(false, "Request Errors"),
                    HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Find a list of products by customer email")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the registered wishlist", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @GetMapping("/{email}")
    public ResponseEntity<List<?>> listProducts(@PathVariable String email) {
        try {
            Wishlist resultEmail = wishlistService.findByUserEmail(email);
            if (resultEmail != null) {
                List<Product> listProducts = resultEmail.getProducts();
                return new ResponseEntity(listProducts, HttpStatus.OK);
            } else {
                return new ResponseEntity(
                        new Response(false, "Cpf not found email: " + resultEmail),
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity(
                    new Response(false, "Request Errors"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Delete a product by idWishlist and idProduct")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the registered wishlist", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @PutMapping("/{idWishlist}/{idProduct}")
    public ResponseEntity<?> deleteProduct(@PathVariable long idWishlist, @PathVariable long idProduct) {
        try {
            Wishlist wishlist = wishlistService.findByIdWishlist(idWishlist);
            if (wishlist == null) {
                return new ResponseEntity<>(
                        new Response(false, "Wishlist not found id: " + idWishlist),
                        HttpStatus.NOT_FOUND);
            } else {
                List<Product> listProductsResult = wishlist.getProducts();
                for (Product product : listProductsResult) {
                    if (product.getId().equals(idProduct)) {
                        listProductsResult.remove(product);

                        wishlistService.subTotal(wishlist);

                        wishlist.setProducts(listProductsResult);

                        Wishlist newListProduct = wishlistService.saveWishlist(wishlist);
                        return new ResponseEntity<>(newListProduct, HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<>(
                    new Response(false, "Product not found id: " + idProduct),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(false, "Request Errors"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}

