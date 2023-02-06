package uk.co.ryanharrison.crudapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.co.ryanharrison.crudapi.model.Product;
import uk.co.ryanharrison.crudapi.model.ProductFilter;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(ProductService.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    private Product product;

    @BeforeEach
    void setUp() {
        this.product = productService.saveProduct(Product.builder()
                .name("name")
                .type("type")
                .createdBy("createdBy")
                .build());
    }

    @Test
    void testGetProductWithFilter() {
        Product product2 = productService.saveProduct(Product.builder()
                        .name("other")
                        .type("type2")
                        .createdBy("createdBy2")
                        .build());
        Page<Product> fullPage = productService.getProductPage(ProductFilter.builder()
                .name("oth%")
                .build(), Pageable.unpaged());
        assertThat(fullPage.getContent()).containsExactly(product2);
        assertThat(fullPage.getTotalElements()).isOne();

        Page<Product> emptyPage = productService.getProductPage(ProductFilter.builder()
                .type("none")
                .build(), Pageable.ofSize(2));
        assertThat(emptyPage.getTotalElements()).isZero();
        assertThat(emptyPage.getContent()).isEmpty();
        assertThat(emptyPage.getSize()).isEqualTo(2);
    }

    @Test
    void testGetProduct() {
        Optional<Product> retrieved = productService.getProduct(product.getId());
        assertThat(retrieved).contains(product);
    }

    @Test
    void testSaveProduct() {
        Product saved = productService.saveProduct(Product.builder()
                .name("name")
                .type("type")
                .createdBy("createdBy")
                .build());
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("name");
        assertThat(saved.getType()).isEqualTo("type");
        assertThat(saved.getCreatedBy()).isEqualTo("createdBy");
    }

    @Test
    void testDeleteProduct() {
        assertThat(productService.getProduct(product.getId())).isNotEmpty();
        productService.deleteProduct(product.getId());
        assertThat(productService.getProduct(product.getId())).isEmpty();
    }

}