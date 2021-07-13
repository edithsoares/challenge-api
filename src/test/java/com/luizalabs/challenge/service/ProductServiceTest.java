package com.luizalabs.challenge.service;

import com.luizalabs.challenge.model.entity.Product;
import com.luizalabs.challenge.model.repository.ProductRepositoryTest;
import com.luizalabs.challenge.model.repository.ProductRepository;
import com.luizalabs.challenge.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
public class ProductServiceTest {

    @SpyBean
    ProductServiceImpl service;

    @MockBean
    ProductRepository repository;


    @Test
    public void saveAProduct(){

        Product productAtSave = ProductRepositoryTest.saveProduct();


        Product productSaved = ProductRepositoryTest.saveProduct();
        productSaved.setId(1l);
        when(repository.save(productAtSave)).thenReturn(productSaved);

        // Execução
        Product product = service.saveProduct(productAtSave);
        assertThat(product.getId()).isEqualTo(productSaved.getId());

    }

//    @Test
//    public void notSaveProduct(){
//        Product productASave = ProductRepositoryTest.saveProduct();
//
//        //execucao e verificacao
//        catchThrowableOfType( () -> service.saveProduct(productASave), BusinessRuleErrors.class );
//        verify(repository, never()).save(productASave);
//    }

    @Test
    public void updateProduct(){
        //cenário
        Product productASave = ProductRepositoryTest.saveProduct();
        productASave.setId(1l);
        productASave.setTitle("Novo Product Salvo");

        when(repository.save(productASave)).thenReturn(productASave);

        //execucao
        service.updateProduct(productASave);

        //verificação
        verify(repository, times(1)).save(productASave);
    }

    @Test
    public void errorUpdateUnsavedProduct() {
        //cenário
        Product product = repository.getById(1l);

        Product result = service.updateProduct(product);

        //execucao e verificacao
        assertThat(result).isNull();

    }

//    @Test
//    public void deleteAProduct() {
//        Product product = Product.builder()
//                .id(1l)
//                .title("Pro")
//                .brand("marca")
//                .price(BigDecimal.valueOf(21))
//                .reviewScore(1)
//                .build();
//
//        service.deleteProduct(product);
//        assertThat(product.getId()).isEqualTo(null);
//    }
@Test
public void deveDeletarUmLancamento() {
    //cenário
    Product product = ProductRepositoryTest.saveProduct();
    product.setId(1l);

    //execucao
    service.deleteProduct(product);

    //verificacao
    verify( repository ).delete(product);
}

    @Test
    public void errorDeleteUnsavedProduct() {
//        deve Lancar Erro Ao Tentar Deletar Um Lancamento Que Ainda Nao Foi Salvo

        //cenário
        Product product = ProductRepositoryTest.saveProduct();

        //execucao
        catchThrowableOfType( () -> service.deleteProduct(product), NullPointerException.class );

        //verificacao
        verify( repository, never() ).delete(product);
    }

    @Test
    public void filterProduct() {
        //cenário
        Product product = ProductRepositoryTest.saveProduct();
        product.setId(1l);

        List<Product> list = Arrays.asList(product);
        when( repository.findAll(any(Example.class)) ).thenReturn(list);

        //execucao
        List<Product> result = service.filterProduct(product);

        //verificacoes
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .contains(product);

    }

    @Test
    public void getAEntryById() {
        //cenário
        Long id = 1l;

        Product product = ProductRepositoryTest.saveProduct();
        product.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(product));

        //execucao
        Product result =  service.getProductById(id);

        //verificacao
        assertThat(result).isNotNull();
    }

    @Test
    public void ReturnEmptyWhenLaunchDoesNotExist() {
        //cenário
        Long id = 1l;

        Product product = ProductRepositoryTest.saveProduct();
        product.setId(id);

        when( repository.findById(id) ).thenReturn( Optional.empty() );

        //execucao
        Optional<Product> result = Optional.ofNullable(service.getProductById(id));

        //verificacao
        assertThat(result.isPresent()).isFalse();
    }

//    @Test
//    public void deveObterSaldoPorUsuario() {
//        //cenario
//        Long idUser = 1l;
//
//        when( repository
//                .obterSaldoPorTipoLancamentoEUsuarioEStatus(idUsuario, TipoLancamento.RECEITA, StatusLancamento.EFETIVADO))
//                .thenReturn(BigDecimal.valueOf(100));
//
//        when( repository
//                .obterSaldoPorTipoLancamentoEUsuarioEStatus(idUsuario, TipoLancamento.DESPESA, StatusLancamento.EFETIVADO))
//                .thenReturn(BigDecimal.valueOf(50));
//
//        //execucao
//        BigDecimal saldo = service.obterSaldoPorUsuario(idUsuario);
//
//        //verificacao
//        assertThat(saldo).isEqualTo(BigDecimal.valueOf(50));
//    }
}
