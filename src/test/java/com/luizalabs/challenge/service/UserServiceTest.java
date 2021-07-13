package com.luizalabs.challenge.service;

import com.luizalabs.challenge.exceptions.ErrorsAuth;
import com.luizalabs.challenge.model.entity.User;
import com.luizalabs.challenge.model.repository.UserRepository;
import com.luizalabs.challenge.service.impl.UserServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest
@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
public class UserServiceTest {

    @SpyBean // Sempre chama o original, a menos que explicitamente determinine o comportamneto desse metodo.
    UserServiceImpl service;

    @MockBean // Nunca chama o método original
    UserRepository repository;

    public static User createUser(){
        return User.builder()
                .email("user@email.com")
                .password("user").build();
    }

    @Test
    public void saveUser(){
        // given
        Mockito.doNothing().when(service).validateEmail(Mockito.anyString());
        User user = User.builder()
                .id(1L)
                .fullName("user")
                .email("user@email.com")
                .password("user").build();
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);

        //when
        User result = service.createUser(new User());

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFullName()).isEqualTo("user");
        assertThat(result.getEmail()).isEqualTo("user@email.com");
        assertThat(result.getPassword()).isEqualTo("user");
    }

    @Test
    public void notSaveUserConflictEmailExist(){
        // given
        String email = "user@email.com";
        User user = User.builder().email(email).build();
        Mockito.doThrow(ServiceException.class).when(service).validateEmail(email);

        // when
        org.junit.jupiter.api.Assertions
                .assertThrows(ServiceException.class, () -> service.createUser(user));

        // then
        Mockito.verify(repository, Mockito.never()).save(user);
    }

    @Test
    public void authenticationUserSuccess(){
        // given
        String email = "user@email.com";
        String password = "user";
        User user = createUser();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        // when
        User result = service.auth(email, password);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void errorAuthenticationUserEmailNotFound(){
        // given
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // when
        Throwable exception = catchThrowable(()-> service.auth("user@email.com", "user"));

        // then
        assertThat(exception).isInstanceOf(ErrorsAuth.class).hasMessage("User Not Found");
    }

    @Test
    public void errorAuthenticationPassword(){
        // given
        String email = "user@email.com";
        String passwordError = "123";
        User user = createUser();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        // when
        Throwable exception = catchThrowable(()-> service.auth(email, passwordError));
        // then
        assertThat(exception).isInstanceOf(ErrorsAuth.class).hasMessage("Senha Inválida");

    }

    @Test
    public void checkExistEmailNotFound(){
        // given
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        // when
        service.validateEmail("user@email.com");
        // then
    }

    @Test
    public void checkExistEmailFound(){
        // given
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        // when
        service.validateEmail("user@email.com");

        // then
    }
}
