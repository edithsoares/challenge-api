package com.luizalabs.challenge.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
//    @NotBlank(message = "Nome é obrigatório")
    private String fullName;

    @NotNull
//    @NotBlank(message = "Email é obrigatório")
    @Column(name = "email")
    private String email;

    @NotNull
//    @NotBlank(message = "Informe uma senha")
    @Column(name = "password")
    @JsonIgnore
    private String password;
}
