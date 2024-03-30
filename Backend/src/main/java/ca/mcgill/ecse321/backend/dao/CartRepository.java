package ca.mcgill.ecse321.backend.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Customer;

public interface CartRepository extends CrudRepository<Cart, Integer> {

	Cart findCartById(Integer id);
		
	Cart removeCartById(Integer id);
	
	Cart findCartByCustomer(Customer customer);
	
	void deleteAll();
}
