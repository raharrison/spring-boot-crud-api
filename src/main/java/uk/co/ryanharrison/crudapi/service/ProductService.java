package uk.co.ryanharrison.crudapi.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.ryanharrison.crudapi.model.Product;
import uk.co.ryanharrison.crudapi.model.ProductFilter;
import uk.co.ryanharrison.crudapi.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

@NullMarked
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<Product> getProductPage(ProductFilter filter, Pageable pageable) {
        return productRepository.findAll(filter.toSpecification(), pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Product> getProduct(UUID id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Optional<Product> updateProduct(UUID id, Product product) {
        return productRepository.findById(id).map(_ -> {
            product.setId(id);
            return productRepository.save(product);
        });
    }

    @Transactional
    public boolean deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }

}
