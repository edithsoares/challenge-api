package com.luizalabs.challenge.controller;

import com.luizalabs.challenge.exceptions.BusinessRuleErrors;
import com.luizalabs.challenge.exceptions.ErrorsAuth;
import com.luizalabs.challenge.model.entity.User;
import com.luizalabs.challenge.payload.Response;
import com.luizalabs.challenge.service.UserService;
import com.luizalabs.challenge.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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


    @ApiOperation(value = "authenticated user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the authenticated user", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @PostMapping("/auth/admin/home")
    public ResponseEntity<?> auth( @RequestBody User user ) {
        try {
            User userAuth = userService.auth(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(userAuth);
        }catch (ErrorsAuth e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @ApiOperation(value = "save a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the registered user", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @PostMapping
    public ResponseEntity<?> saveUser( @RequestBody User user ) {
        try {
            User userSaved = service.createUser(user);
            return new ResponseEntity<>( userSaved, HttpStatus.CREATED);
        }catch (BusinessRuleErrors e) {
            return new ResponseEntity<>(new Response(false,"Request Errors" + e),
                    HttpStatus.BAD_REQUEST);
        }

    }


    @ApiOperation(value = "Search User By Id")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "returns a user by the registered id", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 404, message = "User not found", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try {
            Optional<User> resultId = userService.getById(id);
            if (resultId.isPresent()){
                return new ResponseEntity<>(resultId, HttpStatus.OK);
            }
            return new ResponseEntity<>(new Response(false, "user not found by id" + id),
                    HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(false,"Request Errors"),
                    HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Update User")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "return user updated ", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 404, message = "user not found", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody User user){
        try {
            Optional<User> resultId = Optional.ofNullable(userService.getById(id));
            if (resultId.isPresent()){
                user.setId(resultId.get().getId());
                return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
            }
            return new ResponseEntity<>(new Response(false, "user not found by id" + id),
                    HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(new Response(false,"Request Errors"),
                    HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Delete User by Id")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Returns the ", response = Response.class),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature.", response = Response.class),
            @ApiResponse(code = 404, message = "List products not found", response = Response.class),
            @ApiResponse(code = 500, message = "An exception was thrown", response = Response.class),
    })
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id){
        try {
            User resultId = userService.getById(id);
            if (resultId != null){
                userService.deleteById(resultId);
                return new ResponseEntity<>(new Response(true, "User deleted id: " + id ),
                        HttpStatus.OK);
            }
            return new ResponseEntity<>(new Response(false, "user not found by id" + id),
                    HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(new Response(false,"Request Errors"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
