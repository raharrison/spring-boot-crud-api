package uk.co.ryanharrison.crudapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uk.co.ryanharrison.crudapi.model.Product;
import uk.co.ryanharrison.crudapi.repository.ProductRepository;

import java.time.LocalDateTime;

@SpringBootApplication
public class SpringBootCrudApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCrudApiApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder()
                    .name("product1")
                    .type("car")
                    .createdBy("me")
                    .createdAt(LocalDateTime.now())
                    .build());
            productRepository.save(Product.builder()
                    .name("product2")
                    .type("car")
                    .createdBy("me")
                    .createdAt(LocalDateTime.now())
                    .build());
            productRepository.save(Product.builder()
                    .name("product3")
                    .type("widget")
                    .createdBy("bob")
                    .createdAt(LocalDateTime.now())
                    .build());
        };
    }

}
