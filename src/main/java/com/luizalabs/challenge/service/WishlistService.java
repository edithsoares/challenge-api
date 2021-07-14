package com.luizalabs.challenge.service;

import com.luizalabs.challenge.model.entity.Wishlist;


public interface WishlistService {

    Wishlist saveWishlist(Wishlist wishlist);

    Wishlist findByIdWishlist(long id);

    Wishlist findByUserEmail(String email);

    void sumTotal(Wishlist wishlist);

    void subTotal(Wishlist wishlist);
}
