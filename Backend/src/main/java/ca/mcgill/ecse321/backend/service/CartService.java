package ca.mcgill.ecse321.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.backend.dao.ArtworkRepository;
import ca.mcgill.ecse321.backend.dao.CartRepository;
import ca.mcgill.ecse321.backend.dao.CustomerRepository;
import ca.mcgill.ecse321.backend.model.Artwork;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Customer;

@Service
public class CartService {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	ArtworkRepository artworkRepository;
	
	/**
	 * Creates a cart for the customer and saves it
	 * @param customer The owner of the cart
	 * @return The cart created
	 * @throws IllegalArgumentException If customer is null or customer does not exist
	 */
	@Transactional
	public Cart createCart(Customer customer) {
		String error = "";
		if (customer == null) {
			error = error + "Customer needs to be selected for cart!";
		} else if (customerRepository.findById(customer.getUsername()) == null) {
			error = error + "Customer does not exist!";
		}
		error = error.trim();
		
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		Cart cart = new Cart();
		customer.setCart(cart);		
		cartRepository.save(cart);
		
		return cart;
	}
	
	@Transactional
	public Cart updateCart(Cart cart) {
	    if(cart !=null) {
	    return cartRepository.save(cart);
	    }
	    return null;
	}
	
	
	/**
	 * 
	 * @param cartID non-null The ID of the cart which the artwork will be added to
	 * @param artName non-null The name of the artwork to be added to the cart
	 * @return The updated cart object
	 * @throws IllegalArgumentException If any of the params are null/empty. If no cart with the given cartID exists.
	 * If no artwork with the given artName exists
	 */
	@Transactional
    public Cart addCartArtwork(Integer cartID, String artName) throws IllegalArgumentException {
		String error = "";
		if (cartID == null) {
			error += "Cart ID cannot be null!";
		}
		if (artName == null || artName.trim().length() == 0) {
			error += " artName cannot be null/empty!";
		}
		if (error.trim().length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
        Cart cart = cartRepository.findCartById(cartID);
        if (cart == null) {
        	throw new IllegalArgumentException("No cart with the given cartID exists!");
        }
        
        Artwork artwork = artworkRepository.findArtworkByName(artName);
        if (artwork == null) {
        	throw new IllegalArgumentException("No artwork with the given artName exists!");
        }
        
	    
        cart.addArtwork(artwork);
        return cartRepository.save(cart);
    }
	
	@Transactional
    public Cart removeCartArtwork(Integer ID,Artwork artwork) {
        Cart cart = cartRepository.findCartById(ID);
        if(cart !=null && artwork !=null) {
            cart.getArtwork().remove(artwork);
            return cartRepository.save(cart);
        }
        return null;
    }
	
	
	/**
	 * 
	 * @param cartID non-null The ID of the cart which the artwork will be removed from
	 * @param artName non-null The name of the artwork to be removed from the cart
	 * @return The updated cart object
	 * @throws IllegalArgumentException If any of the params are null/empty. If no cart with the given cartID exists.
	 * If no artwork with the given artName exists
	 */
	@Transactional
    public Cart removeCartArtwork(Integer cartID, String artName) throws IllegalArgumentException {
		String error = "";
		if (cartID == null) {
			error += "Cart ID cannot be null!";
		}
		if (artName == null || artName.trim().length() == 0) {
			error += " artName cannot be null/empty!";
		}
		if (error.trim().length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
        Cart cart = cartRepository.findCartById(cartID);
        if (cart == null) {
        	throw new IllegalArgumentException("No cart with the given cartID exists!");
        }
        
        Artwork artwork = artworkRepository.findArtworkByName(artName);
        if (artwork == null) {
        	throw new IllegalArgumentException("No artwork with the given artName exists!");
        }
        
	    
        cart.getArtwork().remove(artwork);
        return cartRepository.save(cart);
    }
	
	
	
	/**
	 * Gets the cart of the given customer
	 * @param customer The customer whose cart to return
	 * @return The customer's cart
	 * @throws IllegalArgumentException If customer is null or does not exist 
	 */
	@Transactional
	public Cart getCart(Customer customer) {
		String error = "";
		if (customer == null) {
			error = error + "Customer needs to be selected for cart!";
		} else if (customerRepository.findCustomerByUsername(customer.getUsername())==null) {
			error = error + "Customer does not exist!";
		}
		error = error.trim();
		
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		Cart cart = cartRepository.findCartByCustomer(customer);
		
		return cart;
	}
	
	
	/**
	 * @param username non-null The customer's username
	 * @return The customer's cart
	 * @throws IllegalArgumentException If username is null/empty or if no customer with the given username exists
	 */
    @Transactional
    public Cart getCart(String username) throws IllegalArgumentException{
        if (username == null || username.trim().length() == 0) {
           throw new IllegalArgumentException("username cannot be null/empty!");
        } else if (customerRepository.findCustomerByUsername(username) == null) {
        	throw new IllegalArgumentException("Customer with given username does not exist!");
        }
        
        Cart cart = cartRepository.findCartByCustomer(customerRepository.findCustomerByUsername(username));
        return cart;
    }
	

    /**
     * 
     * @param cartID non-null The cart's id
     * @return The cart with the given id
     * @throws IllegalArgumentException If cartID is null or no cart with the given cartID exists
     */
    @Transactional
    public Cart getCart(Integer cartID) throws IllegalArgumentException{
        if (cartID == null) {
        	throw new IllegalArgumentException("cartID cannot be null!");
        }
        
        Cart cart = cartRepository.findCartById(cartID);
        if (cart == null) {
        	throw new IllegalArgumentException("No cart with given cartID exists!");
        }
        return cart;
    }
	
	
}