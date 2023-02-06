package uk.co.ryanharrison.crudapi.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.ryanharrison.crudapi.model.Product;
import uk.co.ryanharrison.crudapi.model.ProductFilter;
import uk.co.ryanharrison.crudapi.service.ProductService;
import uk.co.ryanharrison.crudapi.util.JsonUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final UUID uuid = UUID.randomUUID();
    private final Product product = Product.builder()
            .id(uuid)
            .name("name")
            .type("type")
            .createdBy("createdBy")
            .build();

    @Test
    void testGetProducts() throws Exception {
        ProductFilter filter = ProductFilter.builder()
                .name("name")
                .type("type")
                .build();
        Pageable pageable = Pageable.ofSize(2).withPage(0);
        Page<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);
        when(productService.getProductPage(filter, pageable)).thenReturn(productPage);
        this.mockMvc.perform(get("/products")
                        .queryParam("name", "name")
                        .queryParam("type", "type")
                        .queryParam("size", "2")
                        .queryParam("page", "0")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.writeValue(productPage)));
        verify(productService, times(1)).getProductPage(filter, pageable);
    }

    @Test
    void testGetProduct() throws Exception {
        when(productService.getProduct(uuid)).thenReturn(Optional.of(product));
        var jsonResponse = this.mockMvc.perform(get("/products/{id}", uuid))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(JsonUtils.readObject(jsonResponse, Product.class)).isEqualTo(product);
        verify(productService, times(1)).getProduct(uuid);
    }

    @Test
    void testGetProductNotFound() throws Exception {
        when(productService.getProduct(uuid)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/products/{id}", uuid))
                .andExpect(status().isNotFound());
        verify(productService, times(1)).getProduct(uuid);
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productService.saveProduct(product)).thenReturn(product);
        var jsonResponse = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.writeValue(product)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assertThat(JsonUtils.readObject(jsonResponse, Product.class)).isEqualTo(product);
        verify(productService, times(1)).saveProduct(product);
    }

    @Test
    void testUpdateProduct() throws Exception {
        when(productService.getProduct(uuid)).thenReturn(Optional.of(product));
        when(productService.saveProduct(product)).thenReturn(product);
        var jsonResponse = mockMvc.perform(put("/products/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.writeValue(product)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(JsonUtils.readObject(jsonResponse, Product.class)).isEqualTo(product);
        verify(productService, times(1)).getProduct(uuid);
        verify(productService, times(1)).saveProduct(product);
    }

    @Test
    void testUpdateProductNotFound() throws Exception {
        when(productService.getProduct(uuid)).thenReturn(Optional.empty());
        mockMvc.perform(put("/products/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.writeValue(product)))
                .andExpect(status().isNotFound());
        verify(productService, times(1)).getProduct(uuid);
        verify(productService, times(0)).saveProduct(product);
    }

    @Test
    void testDeleteProduct() throws Exception {
        when(productService.getProduct(uuid)).thenReturn(Optional.of(product));
        this.mockMvc.perform(delete("/products/{id}", uuid))
                .andExpect(status().isOk())
                .andReturn();
        verify(productService, times(1)).deleteProduct(uuid);
    }

    @Test
    void testDeleteProductNotFound() throws Exception {
        when(productService.getProduct(uuid)).thenReturn(Optional.empty());
        this.mockMvc.perform(delete("/products/{id}", uuid))
                .andExpect(status().isNotFound())
                .andReturn();
        verify(productService, times(1)).getProduct(uuid);
        verify(productService, times(0)).deleteProduct(uuid);
    }

}