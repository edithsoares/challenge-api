package com.luizalabs.challenge.model.entity.DTO;

import com.luizalabs.challenge.model.entity.Product;
import com.luizalabs.challenge.model.entity.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wishlist")
public class WishlistDTO {


    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne
    @JoinColumn(name = "idUser")
    private User user;

    @OneToMany
    private List<Product> products;

//    @CreationTimestamp
//    @Column(updatable = false)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="GMT-3")
//    private  date;

    @Column
    @NotNull
    private long Quantity;

    @NotNull
    @Column
    private BigDecimal totalPrice;



}
