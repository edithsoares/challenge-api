package com.luizalabs.challenge.service;

import com.luizalabs.challenge.model.entity.Wishlist;

import java.util.Optional;

public interface WishlistService {

    Wishlist createWishlist(Wishlist wishlist);

    Wishlist updateWishlist(Wishlist wishlist);

    Wishlist findById(long id);

    Optional<Wishlist> getById(Long id);

    void deleteWishlistId(long id);

    Wishlist findByClientEmail(String email);

    Wishlist getWishlistById(long idWishlist);

    void delete(Wishlist wishlist);
}
