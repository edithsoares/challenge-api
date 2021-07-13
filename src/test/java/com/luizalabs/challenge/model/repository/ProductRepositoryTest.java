package com.luizalabs.challenge.model.repository;

import com.luizalabs.challenge.model.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Autowired
    TestEntityManager entityManager;


    @Test
    public void saveAnProduct(){
        // given
        Product product = saveProduct();
        entityManager.persist(product);

        // when
        product = repository.save(product);

        // then
        assertThat(product.getId()).isNotNull();
    }

    @Test
    public void mustDeleteARelease() {
        Product product = saveProduct();
        entityManager.persist(product);

        product = entityManager.find(Product.class, product.getId());

        repository.delete(product);

        Product productNotExist = entityManager.find(Product.class, product.getId());
        assertThat(productNotExist).isNull();
    }

    @Test
    public void UpdateProduct(){
        // given
        Product product = saveProduct();
        entityManager.persist(product);

        // when
        product.setTitle("Product Update");
        product.setBrand("Marca update");
        product.setPrice(BigDecimal.valueOf(105.99));

        Product productAtt = entityManager.find(Product.class, product.getId());

        // then
        assertThat(product.getTitle()).isEqualTo("Product Update");
        assertThat(product.getBrand()).isEqualTo("Marca update");
        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(105.99));
    }

    @Test
    public void findById(){
        //         given
        Product product = saveProduct();
        entityManager.persist(product);

////         when
        Optional<Product> result = repository.findById(product.getId());

////         then
        assertThat(result.isPresent()).isTrue();
    }

    public static Product saveProduct(){
        return Product.builder().title("Product 1")
                .brand("Marca 01")
                .price(BigDecimal.valueOf(245))
                .reviewScore(3).build();
    }

}
