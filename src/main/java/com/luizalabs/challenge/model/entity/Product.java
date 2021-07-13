package com.luizalabs.challenge.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank(message = "Title é obrigatório")
    @Column(name = "title")
    private String title;


    //    @NotBlank(message = "Preço é obrigatório")
    @Column(name = "price")
    private BigDecimal price;

//    @NotBlank(message = "Marca é obrigatório")
    @Column(name = "brand")
    private String brand;

    @Column(name = "reviewScore")
    private int reviewScore;

//    image: URL da imagem do produto

}
