package ca.mcgill.ecse321.backend.dao;

import org.springframework.data.repository.CrudRepository;


import ca.mcgill.ecse321.backend.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {
	
	Customer findCustomerByName(String name);
	
	Customer findCustomerByUsername(String username);
	
	Customer removeCustomerByUsername(String username);
	
	Customer findCustomerById(Integer id);
	
	Customer removeCustomerById(Integer id);
	
    Customer findCustomerByCart_Id(Integer id);
	
	Customer removeCustomerByCart_Id(Integer id);
	
	void deleteAll();
}
