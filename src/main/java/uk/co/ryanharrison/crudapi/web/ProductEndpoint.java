package uk.co.ryanharrison.crudapi.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.ryanharrison.crudapi.model.Product;
import uk.co.ryanharrison.crudapi.model.ProductFilter;
import uk.co.ryanharrison.crudapi.service.ProductService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductEndpoint {

    private final ProductService productService;

    @GetMapping("/products")
    public Page<Product> getProducts(ProductFilter filter, Pageable pageable) {
        return productService.getProductPage(filter, pageable);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable UUID id) {
        return ResponseEntity.of(productService.getProduct(id));
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product saved = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        return productService.getProduct(id).map(p -> {
                    product.setId(id);
                    return ResponseEntity.ok(productService.saveProduct(product));
                }
        ).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        return productService.getProduct(id).map(p -> {
                    productService.deleteProduct(id);
                    return ResponseEntity.ok().build();
                }
        ).orElse(ResponseEntity.notFound().build());
    }

}
