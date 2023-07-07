package pet.store.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pet.store.entity.Customer;
import pet.store.entity.PetStore;

@Repository
public interface PetStoreDao extends JpaRepository<PetStore, Long> {

	Optional<Customer> findCustomerById(Long customerId);
}
