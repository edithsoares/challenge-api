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
        Optional<User> usuario = repository.findByEmail(email);

        if(!usuario.isPresent()) {
            throw new ErrorsAuth("Usuário não encontrado para o email informado.");
        }

        if(!usuario.get().getPassword().equals(senha)) {
            throw new ErrorsAuth("Senha inválida.");
        }

        return usuario.get();
    }

    public User createUser(User user){
        Objects.requireNonNull(user.getId());
        validateEmail(user.getEmail());
        return repository.save(user);
    }

    public User getByEmail(String email){
        return repository.getByEmail(email);
    }

    public User getById(long id){
        return repository.findById(id);
    }

    public User updateUser(User user){
        Objects.requireNonNull(user);
        return repository.save(user);
    }

    public void deleteById(User user){
        repository.delete(user);
    }


//    Implementar no Controller
//    public List<User> listAllClient(){
//        return repository.findAll();
//    }

    @Override
    public void validateEmail(String email) {
        boolean exists = repository.existsByEmail(email);
        if (exists){
            throw new BusinessRuleErrors("Email já esta sendo utilizado em outra conta.");
        }
    }
}
