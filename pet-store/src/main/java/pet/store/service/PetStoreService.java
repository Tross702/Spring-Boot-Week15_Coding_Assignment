package pet.store.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	private final PetStoreDao petStoreDao;
	private final EmployeeDao employeeDao;
	private final CustomerDao customerDao;

	@Autowired
	public PetStoreService(PetStoreDao petStoreDao, EmployeeDao employeeDao, CustomerDao customerDao) {
		this.petStoreDao = petStoreDao;
		this.employeeDao = employeeDao;
		this.customerDao = customerDao;
	}

	public PetStoreData savePetStore(PetStoreData petStoreData) {
		PetStore petStore = findOrCreatePetStore(petStoreData.getId());
		copyPetStoreFields(petStore, petStoreData);
		PetStore savedPetStore = petStoreDao.save(petStore);
		return new PetStoreData(savedPetStore);
	}

	public PetStoreData updatePetStore(Long petStoreId, PetStoreData petStoreData) {
		PetStore petStore = findPetStoreById(petStoreId);
		if (petStore == null) {
			throw new NoSuchElementException("Pet store not found with ID: " + petStoreId);
		}
		copyPetStoreFields(petStore, petStoreData);
		PetStore updatedPetStore = petStoreDao.save(petStore);
		return new PetStoreData(updatedPetStore);
	}

	public PetStoreData addEmployeeToPetStore(Long petStoreId, Long employeeId) {
		PetStore petStore = findPetStoreById(petStoreId);
		Employee employee = findEmployeeById(employeeId);
		if (petStore == null || employee == null) {
			throw new NoSuchElementException(
					"Pet store or employee not found with ID: " + petStoreId + ", " + employeeId);
		}
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		employeeDao.save(employee);
		return new PetStoreData(petStore);
	}

	public PetStoreData addCustomerToPetStore(Long petStoreId, Long customerId) {
		PetStore petStore = findPetStoreById(petStoreId);
		Customer customer = findCustomerById(customerId);
		if (petStore == null || customer == null) {
			throw new NoSuchElementException(
					"Pet store or customer not found with ID: " + petStoreId + ", " + customerId);
		}
		petStore.getCustomers().add(customer);
		customer.getPetStores().add(petStore);
		petStoreDao.save(petStore);
		return new PetStoreData(petStore);
	}

	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> petStoreDataList = new ArrayList<>();
		for (PetStore petStore : petStores) {
			PetStoreData petStoreData = new PetStoreData(petStore);
			petStoreData.setCustomers(new HashSet<>());
			petStoreData.setEmployees(new HashSet<>());
			petStoreDataList.add(petStoreData);
		}
		return petStoreDataList;
	}

	public PetStoreData retrievePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		if (petStore == null) {
			throw new NoSuchElementException("Pet store not found with ID: " + petStoreId);
		}
		return new PetStoreData(petStore);
	}

	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		if (petStore != null) {
			petStoreDao.delete(petStore);
		}
	}

	@Transactional
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee employee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Employee newEmployee = new Employee();
		newEmployee.setEmployeeName(employee.getEmployeeName());
		newEmployee.setPetStore(petStore);
		petStore.getEmployees().add(newEmployee);
		Employee savedEmployee = employeeDao.save(newEmployee);
		return new PetStoreEmployee(savedEmployee.getId(), savedEmployee.getEmployeeName());
	}

	private PetStore findPetStoreById(Long petStoreId) {
		Optional<PetStore> optionalPetStore = petStoreDao.findById(petStoreId);
		if (optionalPetStore.isPresent()) {
			return optionalPetStore.get();
		}
		return null;
	}

	@Transactional
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer customer) {
		PetStore petStore = findPetStoreById(petStoreId);
		Customer newCustomer = new Customer();
		newCustomer.setCustomerEmail(customer.getCustomerEmail());
		newCustomer.getPetStores().add(petStore);
		petStore.getCustomers().add(newCustomer);
		Customer savedCustomer = customerDao.save(newCustomer);
		return new PetStoreCustomer(savedCustomer.getId(), savedCustomer.getCustomerEmail());
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		if (petStoreId != null) {
			Optional<PetStore> optionalPetStore = petStoreDao.findById(petStoreId);
			if (optionalPetStore.isPresent()) {
				return optionalPetStore.get();
			}
		}
		return new PetStore();
	}

	private Employee findEmployeeById(Long employeeId) {
		Optional<Employee> optionalEmployee = employeeDao.findById(employeeId);
		if (optionalEmployee.isPresent()) {
			return optionalEmployee.get();
		}
		throw new NoSuchElementException("Employee not found with ID: " + employeeId);
	}

	private Customer findCustomerById(Long customerId) {
		Optional<Customer> optionalCustomer = customerDao.findById(customerId);
		if (optionalCustomer.isPresent()) {
			return optionalCustomer.get();
		}
		throw new NoSuchElementException("Customer not found with ID: " + customerId);
	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setStoreName(petStoreData.getStoreName());
	}

	public void deleteEmployeeById(Long employeeId) {
		Employee employee = findEmployeeById(employeeId);
		if (employee != null) {
			employeeDao.delete(employee);
		}
	}

	public void deleteCustomerById(Long customerId) {
		Customer customer = findCustomerById(customerId);
		if (customer != null) {
			customerDao.delete(customer);
		}
	}
}
