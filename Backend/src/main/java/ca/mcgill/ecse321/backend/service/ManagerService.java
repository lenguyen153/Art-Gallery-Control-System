package ca.mcgill.ecse321.backend.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.backend.dao.ArtistRepository;
import ca.mcgill.ecse321.backend.dao.ArtworkRepository;
import ca.mcgill.ecse321.backend.dao.CartRepository;
import ca.mcgill.ecse321.backend.dao.CustomerRepository;
import ca.mcgill.ecse321.backend.dao.ManagerRepository;
import ca.mcgill.ecse321.backend.dao.TransactionRepository;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Artwork;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Customer;
import ca.mcgill.ecse321.backend.model.Manager;
import ca.mcgill.ecse321.backend.model.Transaction;

@Service
public class ManagerService {

	@Autowired
	ManagerRepository managerRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ArtistRepository artistRepository;
	
	@Autowired
    ArtworkRepository artworkRepository;
	
	@Autowired
    CartRepository cartRepository;
	
	@Autowired
    TransactionRepository transactionRepository;

	/**
     * Creates a new manager with the given parameters.
     * 
     * @param name The name of the manager
     * @param username The username of the manager
     * @param password The password of the manager
     * @return The created manager
     * @throws IllegalArgumentException If name, username or password are null/empty strings. Or if the username is already taken by another manager.
     */
	@Transactional
	public Manager createManager(String name, String username, String password) {
	    String error = "";
        if (username == null || username.trim().length() == 0) {
            error += "username cannot be empty ";
        }
        if (password == null|| password.trim().length() == 0) {
            error += "password cannot be empty ";
        }
        
        if (name == null|| name.trim().length() == 0) {
            error += "name cannot be empty";
        }
        if(error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        
        Manager manager = managerRepository.findManagerByUsername(username);
        if (manager != null) {
        	throw new IllegalArgumentException("Manager with username:" + username + "already exists!");
        }
		
		manager = new Manager();
		manager.setName(name);
		manager.setUsername(username);
		manager.setPassword(password);
		
		return managerRepository.save(manager);
	}
	
	/**
	 * Returns a manager object.
	 * @param name The username of the manager
	 * @return The manager or null if he does not exist
	 */
	@Transactional
	public Manager getManager(String name) {
	    if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Manager name cannot be empty!");
        }
		return managerRepository.findManagerByUsername(name);
	}
	
	/**
	 * 
	 * @return A list containing all artists
	 */
	@Transactional
	public List<Artist> getAllArtists() {
		return ServiceUtils.toList(artistRepository.findAll());
	}
	
	/**
	 * 
	 * @return A list containing all managers
	 */
	@Transactional
	public List<Manager> getAllManagers() {
		return ServiceUtils.toList(managerRepository.findAll());
	}
	
	/**
	 * 
	 * @return A list containing all customers
	 */
	@Transactional
	public List<Customer> getAllCustomers() {
		return ServiceUtils.toList(customerRepository.findAll());
	}
	
	/**
	 * 
	 * @return A list containing all artworks
	 */
	@Transactional
    public List<Artwork> getAllArtworks() {
        return ServiceUtils.toList(artworkRepository.findAll());
    }
	
	/**
	 * 
	 * @return A list containing all carts
	 */
	@Transactional
    public List<Cart> getAllCarts() {
        return ServiceUtils.toList(cartRepository.findAll());
    }
	
	@Transactional
    public List<Transaction> getAllTransactions() {
        return ServiceUtils.toList(transactionRepository.findAll());
    }
	
	/**
	 * Deletes the manager with the given username
	 * @param username The username of the manager
	 * @return The deleted manager or null if he did not exist
	 */
	@Transactional
    public Manager deleteManager(String username) {
	    if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Manager name cannot be empty!");
        }
      Manager manager=null;
      if((manager = managerRepository.findManagerByUsername(username)) != null) {
          managerRepository.delete(manager);
          return manager;
      }
      return null;
    }
	
	/**
	 * Updates the specific commision of an artist.
	 * @param username The username of the artist which should receive a new specific commision.
	 * @param specificCommision The new commision of the artist
	 * @return The artist which received the change or null if he did not exist
	 */
    @Transactional
    public Artist updateCommission(String username, Double specificCommision) {
        if (username == null || username.trim().length() == 0 || specificCommision == null) {
            throw new IllegalArgumentException("Artist name cannot be empty!");
        }
        Artist artist = null;
        if ((artist = artistRepository.findArtistByUsername(username)) != null && specificCommision >= 0) {
            artist.setSpecificCommisionApplied(specificCommision);
            artist = artistRepository.save(artist);
            return artistRepository.save(artist);
            
        }
        
        return null;
    }
	
    /**
     * Updates a manager account with new information
     * @param name The new name of the manager
     * @param username The username of the manager which will received new account details
     * @param password The new password of the manager
     * @return The manager account which received the changes or null if the account did not exist
     */
	@Transactional
    public Manager updateManager(String name, String username, String password) {
	    if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("username name cannot be empty!");
        }
      Manager manager = managerRepository.findManagerByUsername(username);
      if(manager != null) {
          managerRepository.findManagerByUsername(username).setName(name);
          managerRepository.findManagerByUsername(username).setPassword(password);
          return manager;
      }
      return null;
    }
	
	/**
	 * Bans a manager.
	 * @param name The username of the manager to be banned
	 */
	@Transactional
	public void banManager(String name) {
		 customerRepository.findCustomerByUsername(name).setIsBanned(true);
	}
	
	/**
	 * Unbans a manager.
	 * @param name The username of the manager to be unbanned
	 */
	@Transactional
	public void unbanManager(String name) {
		 customerRepository.findCustomerByUsername(name).setIsBanned(false);
	}
	
}
