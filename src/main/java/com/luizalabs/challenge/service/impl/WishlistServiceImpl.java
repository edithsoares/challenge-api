package com.luizalabs.challenge.service.impl;

import com.luizalabs.challenge.model.entity.Product;
import com.luizalabs.challenge.model.entity.Wishlist;
import com.luizalabs.challenge.model.repository.WishlistRepository;
import com.luizalabs.challenge.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;


@Service
public class WishlistServiceImpl implements WishlistService {


    @Autowired
    private WishlistRepository wishlistRepository;

    public Wishlist saveWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    public Wishlist findByIdWishlist(long id) {
        return wishlistRepository.findById(id);
    }

    public Wishlist findByUserEmail(String email) {
        return wishlistRepository.findByUserEmail(email);
    }


    //    Subtrai o valor do produto removido
    public void subTotal(Wishlist wishlist) {
        BigDecimal total = BigDecimal.ZERO;
        List<Product> listProduct = wishlist.getProducts();
        for (Product product : listProduct) {
            total = wishlist.getTotalPrice().subtract(product.getPrice());
        }
        wishlist.setTotalPrice(total);
    }


    // Subtrai o valor total dos produto adicionados na lista
    public void sumTotal(Wishlist wishlist) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Product> listProduct = wishlist.getProducts();
        for (Product product : listProduct) {
            totalPrice = totalPrice.add(product.getPrice(), new MathContext(5));
        }
        wishlist.setTotalPrice(totalPrice);
    }
}
