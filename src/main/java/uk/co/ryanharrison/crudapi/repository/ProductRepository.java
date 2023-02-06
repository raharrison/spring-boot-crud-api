package uk.co.ryanharrison.crudapi.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.ryanharrison.crudapi.model.Product;

import java.util.UUID;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

}
