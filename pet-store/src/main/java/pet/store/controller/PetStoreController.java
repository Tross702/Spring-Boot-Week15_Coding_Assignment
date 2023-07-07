package pet.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
public class PetStoreController {

    private final PetStoreService petStoreService;

    @Autowired
    public PetStoreController(PetStoreService petStoreService) {
        this.petStoreService = petStoreService;
    }

    @PostMapping("/{petStoreId}/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreEmployee addEmployeeToPetStore(
            @PathVariable Long petStoreId,
            @RequestBody PetStoreEmployee employee
    ) {
        return petStoreService.saveEmployee(petStoreId, employee);
    }

    @PostMapping("/{petStoreId}/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreCustomer addCustomerToPetStore(
            @PathVariable Long petStoreId,
            @RequestBody PetStoreCustomer customer
    ) {
        return petStoreService.saveCustomer(petStoreId, customer);
    }

    @GetMapping
    public List<PetStoreData> getAllPetStores() {
        return petStoreService.retrieveAllPetStores();
    }

    @GetMapping("/{petStoreId}")
    public PetStoreData getPetStoreById(@PathVariable Long petStoreId) {
        return petStoreService.retrievePetStoreById(petStoreId);
    }

    @DeleteMapping("/{petStoreId}")
    public void deletePetStoreById(@PathVariable Long petStoreId) {
        petStoreService.deletePetStoreById(petStoreId);
    }
}
