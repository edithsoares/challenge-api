package com.luizalabs.challenge.service.impl;

import com.luizalabs.challenge.exceptions.BusinessRuleErrors;
import com.luizalabs.challenge.model.entity.Product;
import com.luizalabs.challenge.model.entity.Wishlist;
import com.luizalabs.challenge.model.repository.WishlistRepository;
import com.luizalabs.challenge.service.WishlistService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {


    @Autowired
    WishlistRepository repository;
//    private WishlistRepository repository;
//
//    public WishlistServiceImpl(WishlistRepository repository) {
//        this.repository = repository;
//    }

    public Wishlist createWishlist(Wishlist wishlist){
        Objects.requireNonNull(wishlist);

        return repository.save(wishlist);
    }

    public Wishlist updateWishlist(Wishlist wishlist){
        Objects.requireNonNull(wishlist);
        return repository.save(wishlist);
    }

    @Override
    public Wishlist findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Wishlist> getById(Long id) {
        return repository.findById(id);
    }

    public Wishlist getWishlistById(long id){
        return repository.findById(id);
    }

    @Override
    public void delete(Wishlist wishlist) {
        Objects.requireNonNull(wishlist.getId());
        repository.delete(wishlist);
    }

    public void deleteWishlistId(long id){
        repository.deleteById(id);
    }

    @Override
    public Wishlist findByClientEmail(String email) {
        return null;
    }

    public Wishlist findByUserEmail(String email) {
        return repository.findByUserEmail(email);
    }



}
