package pet.store.controller.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetStoreCustomer {
	private Long id;
	private String customerEmail;
	private Set<PetStore> petStores = new HashSet<>();

	public PetStoreCustomer(Long id, String customerEmail) {
		this.id = id;
		this.customerEmail = customerEmail;
	}

	public void addPetStore(PetStore petStore) {
		petStores.add(petStore);
		petStore.getCustomers().add(this);
	}

	public void removePetStore(PetStore petStore) {
		petStores.remove(petStore);
		petStore.getCustomers().remove(this);
	}
}
