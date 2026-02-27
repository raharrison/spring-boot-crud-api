package uk.co.ryanharrison.crudapi.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.ryanharrison.crudapi.model.Product;

import java.util.UUID;

@NullMarked
@Repository
public interface ProductRepository extends ListCrudRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

}
