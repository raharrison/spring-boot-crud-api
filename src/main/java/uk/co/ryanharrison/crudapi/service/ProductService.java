package uk.co.ryanharrison.crudapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.ryanharrison.crudapi.model.Product;
import uk.co.ryanharrison.crudapi.model.ProductFilter;
import uk.co.ryanharrison.crudapi.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

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
    public void deleteProduct(UUID uuid) {
        productRepository.deleteById(uuid);
    }

}
