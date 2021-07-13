package com.luizalabs.challenge.controller;

import com.luizalabs.challenge.exceptions.ErrorsAuth;
import com.luizalabs.challenge.model.entity.User;
import com.luizalabs.challenge.service.UserService;
import com.luizalabs.challenge.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    // Documentação / Refatoração para melhorias

    private final UserService service;
//    private final LancamentoService lancamentoService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/autenticar")
    public ResponseEntity<?> autenticar( @RequestBody User user ) {
        try {
            User usuarioAutenticado = userService.auth(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(usuarioAutenticado);
        }catch (ErrorsAuth e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/user")
//    public ResponseEntity<?> createUser(@RequestBody User user){
//        try {
//            User resultEmail = userService.getByEmail(user.getEmail());
//            if (resultEmail != null){
//                return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
//            }
//            return new ResponseEntity<>("email já cadastrdo", HttpStatus.CONFLICT);
//        }catch (Exception ex){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping
    public ResponseEntity<?> saveUser( @RequestBody User user ) {

//        User usuario = Usuario.builder()
//                .nome(dto.getNome())
//                .email(dto.getEmail())
//                .senha(dto.getSenha()).build();

        try {
            User userSave = userService.createUser(user);
            return new ResponseEntity(userSave, HttpStatus.CREATED);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getUserById(@PathVariable Long id){
        try {

            Optional<User> resultId = Optional.ofNullable(userService.getById(id));
            if (resultId.isPresent()){
                return new ResponseEntity<>(resultId, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/user")
//    public ResponseEntity<?> searchAllUser(){
//        try {
//            List<User> resultUsers = userService.getAllUsers();
//        }
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody User user){
        try {
            Optional<User> resultId = Optional.ofNullable(userService.getById(id));
            if (resultId.isPresent()){
                user.setId(resultId.get().getId());
                return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id){
        try {
            User resultId = userService.getById(id);
            if (resultId != null){
                userService.deleteById(resultId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
