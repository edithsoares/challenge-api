package com.luizalabs.challenge.model.repository;


import com.luizalabs.challenge.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    User getByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
