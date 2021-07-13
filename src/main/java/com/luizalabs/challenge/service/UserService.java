package com.luizalabs.challenge.service;

import com.luizalabs.challenge.model.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User auth(String email, String password);

    User getById(long id);

    User updateUser(User user);

    void deleteById(User user);

//    List<User> listAllClient();

    void validateEmail(String email);



}
