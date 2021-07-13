package com.luizalabs.challenge.model.repository;

import com.luizalabs.challenge.model.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Wishlist findById(long id);

    Wishlist findByUserEmail(String email);

}
