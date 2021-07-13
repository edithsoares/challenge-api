package com.luizalabs.challenge.model.repository;


import com.luizalabs.challenge.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void existsEmail(){
//         Deve retornar True ao encontrar email cadastrado

//         given
        User user = createUser();
        entityManager.persist(user);

//         when
        boolean result = repository.existsByEmail("user@email.com");

//         then
        assertThat(result).isTrue();
    }

    @Test
    public void emailNotRegistered(){
        // Deve retorna false ao não encontrar email cadastrado

        // given

        // when
        boolean result = repository.existsByEmail("user@email.com");

        // then
        assertThat(result).isFalse();

    }

    @Test
    public void saveUserAtDataBase(){
        // Deve retorna o id cadastro na base

        // given
        User user = createUser();

        // when
        User savedUser = repository.save(user);

        // then
        assertThat(savedUser.getId()).isNotNull();
    }


    @Test
    public void searchUserByEmail(){
        // Deve buscar um user por email retornando True

        // given
        User user = createUser();
        entityManager.persist(user);

        // when
        Optional<User> result = repository.findByEmail("user@email.com");

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void notFoundUserByEmail(){
        // given

        // when
        Optional<User> result = repository.findByEmail("user@email.com");

        // then
        assertThat(result.isPresent()).isFalse();

    }

    @Test
    public void deleteById(){
        // given
        User user = createUser();
        entityManager.persist(user);

        // when
        repository.deleteById(user.getId());

        Optional<User> result = repository.findById(user.getId());

        // then
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void updateUser(){

    }

    @Test
    public void getAllUsers(){
        // given
        User user = createUser();
        entityManager.persist(user);

        // when

        List<User> result = repository.findAll();

        // then
        assertThat(result).isNotNull();
    }

    public static User createUser(){
        return User.builder()
                .fullName("User Silva")
                .email("user@email.com")
                .password("pass123").build();
    }

}
