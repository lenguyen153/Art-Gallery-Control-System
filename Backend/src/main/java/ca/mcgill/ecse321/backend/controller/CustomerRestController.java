package ca.mcgill.ecse321.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.backend.dto.CartDto;
import ca.mcgill.ecse321.backend.dto.CustomerDto;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Customer;
import ca.mcgill.ecse321.backend.service.CartService;
import ca.mcgill.ecse321.backend.service.CustomerService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CartService cartService;
	
	/**
	 * Creates a customer with the given parameters
	 * @param customerDto The DTO containing the necessary attributes to create a customer (name, username and password)
	 * @return The new CustomerDto created
	 * @throws IllegalArgumentException If name, username or password are null/empty strings. Or if the username is already taken by another customer.
	 */
	@PostMapping(value= { "/createCustomer", "/createCustomer/" })
    public CustomerDto createcustomer(@RequestBody CustomerDto customerDto) throws IllegalArgumentException {
        Customer customer = customerService.createCustomer(
                       	customerDto.getName(),
                        customerDto.getUsername(),
                        customerDto.getPassword());
        return ControllerUtils.convertToDto(customer);
    }
	
	/**
	 * @param username The customer's username
	 * @return The specified CustomerDTO 
	 * @throws IllegalArgumentException If username is null/empty string, or if no customer with the given username exists 
	 */
	@GetMapping(value = {"/getCustomer/{username}", "/getCustomer/{username}/"})
    public CustomerDto getCustomer(@PathVariable String username) throws IllegalArgumentException {
        return ControllerUtils.convertToDto(customerService.getCustomer(username));
    }
	
	/**
	 * 
	 * @param username non-null The username of the customer to delete
	 * @return The delete customer's DTO
	 * @throws IllegalArgumentException If the given username is null/empty or it does not correspond to any customer
	 */
	@PostMapping(value = {"/deleteCustomer/{username}","/deleteCustomer/{username}/"})
    public CustomerDto deleteCustomer(@PathVariable String username) throws IllegalArgumentException{
        Customer customer = customerService.deleteCustomer(username);
        return ControllerUtils.convertToDto(customer);
        
    }
	
	/**
	 * Adds the artwork with the given parameter name to the cart of the customer with the given parameter username.
	 * @param customerUsername non-null
	 * @param artName non-null
	 * @return The updated CartDto 
	 * @throws IllegalArgumentException If any of the params are null/empty. If the customerUsername does not correspond to a customer. If the artName does
	 * not correspond to an artwork
	 */
	@PostMapping(value = { "/addToCart", "/addToCart/" })
    public CartDto addToCart(@RequestParam String customerUsername,@RequestParam String artName) throws IllegalArgumentException{
	    artName = artName.replace('-', ' ');
        Customer customer = customerService.getCustomer(customerUsername);
        Cart cart = cartService.addCartArtwork(customer.getCart().getId(), artName);
        return ControllerUtils.convertToDto(cart);
    }
	
	/**
	 * Removes the artwork with the given name from the customer's with the given username cart.
	 * @param customerUsername non-null
	 * @param artName non-null
	 * @return The updated CartDto 
	 * @throws IllegalArgumentException If any of the params are null/empty. If the customerUsername does not correspond to a customer. If the artName does
	 * not correspond to an artwork
	 */
	@PostMapping(value= { "/removeFromCart", "/removeFromCart/" })
    public CartDto removeFromCart(@RequestParam String customerUsername,@RequestParam String artName) {
        Customer customer = customerService.getCustomer(customerUsername);
        Cart cart = cartService.removeCartArtwork(customer.getCart().getId(), artName);
        return ControllerUtils.convertToDto(cart);
    }
	
    
}
