package com.luizalabs.challenge.service.impl;

import com.luizalabs.challenge.exceptions.BusinessRuleErrors;
import com.luizalabs.challenge.exceptions.ErrorsAuth;
import com.luizalabs.challenge.model.entity.User;
import com.luizalabs.challenge.model.repository.UserRepository;
import com.luizalabs.challenge.service.UserService;
import org.aspectj.bridge.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository repository;


    @Override
    public User auth(String email, String senha) {
        Optional<User> user = repository.findByEmail(email);

        if(!user.isPresent()) {
            throw new ErrorsAuth("Usuário não encontrado para o email informado.");
        }

        if(!user.get().getPassword().equals(senha)) {
            throw new ErrorsAuth("Senha inválida.");
        }

        return user.get();
    }

    @Override
    public User createUser(User user){
        validateEmail(user.getEmail());
        return repository.save(user);
    }

    @Override
    public User getById(long id){
        return repository.findById(id);
    }

    @Override
    public Optional<User> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public User updateUser(User user){
        Objects.requireNonNull(user);
        return repository.save(user);
    }

    @Override
    public void deleteById(User user){
        repository.delete(user);
    }


    @Override
    public void validateEmail(String email) {
        boolean exists = repository.existsByEmail(email);
        if (exists){
            throw new BusinessRuleErrors("Email já esta sendo utilizado em outra conta.");
        }
    }
}
