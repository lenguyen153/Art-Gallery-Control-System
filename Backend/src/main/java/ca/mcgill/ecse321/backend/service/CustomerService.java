package ca.mcgill.ecse321.backend.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.backend.dao.ArtistRepository;
import ca.mcgill.ecse321.backend.dao.ArtworkRepository;
import ca.mcgill.ecse321.backend.dao.CartRepository;
import ca.mcgill.ecse321.backend.dao.CustomerRepository;
import ca.mcgill.ecse321.backend.dao.TransactionRepository;
import ca.mcgill.ecse321.backend.model.ArtStyle;
import ca.mcgill.ecse321.backend.model.Artwork;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Customer;
import ca.mcgill.ecse321.backend.model.Transaction;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ArtworkRepository artworkRepository;

    
    /**
     * Creates a new customer with the given parameters.
     * 
     * @param name The name of the customer
     * @param username The username of the customer
     * @param password The password of the customer
     * @return The created customer
     * @throws IllegalArgumentException If name, username or password are null/empty strings. Or if the username is already taken by another customer.
     */
    @Transactional
    public Customer createCustomer(String name, String username, String password) throws IllegalArgumentException {
        String error = "";
        if (username == null || username.trim().length() == 0) {
            error += "username cannot be empty! ";
        }
        if (password == null || password.trim().length() == 0) {
            error += "password cannot be empty! ";
        }

        if (name == null || name.trim().length() == 0) {
            error += "name cannot be empty! ";
        }
        
        error = error.trim();
        if (error.length() > 0) {
        	throw new IllegalArgumentException(error);
        }
        
        if(customerRepository.findCustomerByUsername(username) != null) {
            throw new IllegalArgumentException("The username is already taken");
        }
        

        Customer customer = new Customer();
        customer.setName(name);
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setCart(new Cart());    //method also sets the cart's customer to this customer
        return customerRepository.save(customer);
    }

    /**
     * Returns a customer object.
     * @param username non-null The customer's username
     * @return The Customer object with the given username
     * @throws IllegalArgumentException If username is null/empty string, or if no customer with the given username exists 
     */
    @Transactional
    public Customer getCustomer(String username) throws IllegalArgumentException {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Customer name cannot be empty!");
        }
        
        Customer customer = customerRepository.findCustomerByUsername(username);
        
        // check if there is a customer with given username
        if (customer == null) {
        	throw new IllegalArgumentException("No customer with username:" + username + " exists!");
        }
        
        return customer;
    }

    /**
     * 
     * @param username non-null The username of the customer 
     * @return The Customer object that was deleted
     * @throws IllegalArgumentException If the given username is null/empty or it does not correspond to any customer
     */
    @Transactional
    public Customer deleteCustomer(String username) throws IllegalArgumentException {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Customer name cannot be empty!");
        }
        Customer customer = customerRepository.findCustomerByUsername(username);
        if (customer != null) {
            List<Transaction> list = customer.getTransaction();
            for (int i = 0; i < list.size(); i++) {
                // remove from customer list
                Transaction transaction = list.get(i);
                transaction.getArtist().getTransaction().remove(transaction);
                artistRepository.save(transaction.getArtist());
                transactionRepository.delete(transaction);
            }
            customerRepository.delete(customer);
            return customer;
        } else {
        	throw new IllegalArgumentException("No customer with username:" + username + " exists!");
        }
    }
    
	/**
	 * 
	 * @return A list containing all artworks
	 */
	@Transactional
    public List<Artwork> getAllArtworks() {
        return ServiceUtils.toList(artworkRepository.findAll());
    } 
	

}
