package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreData {
	private Long id;
	private String storeName;
	private Set<PetStoreCustomer> customers = new HashSet<>();
	private Set<PetStoreEmployee> employees = new HashSet<>();

	public PetStoreData(PetStore petStore) {
		this.id = petStore.getId();
		this.storeName = petStore.getStoreName();
		this.customers = mapCustomersToDTO(petStore.getCustomers());
		this.employees = mapEmployeesToDTO(petStore.getEmployees());
	}

	private Set<PetStoreCustomer> mapCustomersToDTO(Set<Customer> customers) {
		Set<PetStoreCustomer> customerDTOs = new HashSet<>();
		if (customers != null) {
			for (Customer customer : customers) {
				PetStoreCustomer customerDTO = new PetStoreCustomer(customer.getId(), customer.getCustomerEmail());
				customerDTOs.add(customerDTO);
			}
		}
		return customerDTOs;
	}

	private Set<PetStoreEmployee> mapEmployeesToDTO(Set<Employee> employees) {
		Set<PetStoreEmployee> employeeDTOs = new HashSet<>();
		if (employees != null) {
			for (Employee employee : employees) {
				PetStoreEmployee employeeDTO = new PetStoreEmployee(employee.getId(), employee.getEmployeeName());
				employeeDTOs.add(employeeDTO);
			}
		}
		return employeeDTOs;
	}

	@Data
	@NoArgsConstructor
	public static class PetStoreCustomer {
		private Long id;
		private String customerEmail;

		public PetStoreCustomer(Long id, String customerEmail) {
			this.id = id;
			this.customerEmail = customerEmail;
		}
	}
}
