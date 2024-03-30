package ca.mcgill.ecse321.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.backend.dto.CartDto;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.service.CartService;

@CrossOrigin(origins = "*")
@RestController
public class CartRestController {
	
	@Autowired
	private CartService cartService;
	
	/**
	 * Returns the CartDto. Uses the path variable customer, which is the
	 * customer
	 * @param customerUsername The cart's customer's username
	 * @return The customer's cart
	 */
	@GetMapping(value = { "/{customerUsername}/cart", "/{customerUsername}/cart/" })
	public CartDto getCustomerCart(@PathVariable String customerUsername) {
		Cart cart = cartService.getCart(customerUsername);
		return ControllerUtils.convertToDto(cart);
	}
	
	/*
	 * Adds an artwork to the cart. 
	 * Requires the cart and artwork to be specified in the request's body
	 * @param cartDto The cart in which to add
	 * @param artworkDto The artwork to add
	 * @return The updated cart with the artwork now added
	 */
	/*@PutMapping(value = { "/addToCart", "/addToCart/" })
	public CartDto addArtworkToCart(@RequestBody CartDto cartDto, 
			@RequestBody ArtworkDto artworkDto) {
		Cart cart = cartService.getCart(cartDto.getCustomer().getUsername());
		Artwork artwork = artworkService.getArtwork(artworkDto.getName());
		return ControllerUtils.convertToDto(cartService.addArtworkToCart(cart, artwork));
	}*/
	
	
	/*
	 * Removes the specified artwork from the cart
	 * @param cartDto The cart from which to remove the artwork
	 * @param artworkDto The artwork to remove
	 * @return The updated cart
	 */
	/*@DeleteMapping(value = { "/removeFromCart", "/removeFromCart/"})
	public CartDto removeArtworkFromCart(@RequestBody CartDto cartDto,
			@RequestBody ArtworkDto artworkDto) {
		Cart cart = cartService.getCart(cartDto.getCustomer().getUsername());
		Artwork artwork = artworkService.getArtwork(artworkDto.getName());
		return ControllerUtils.convertToDto(cartService.removeArtworkFromCart(cart, artwork));
	}*/
	
	/*
	 * Clears the given cart, leaving it empty
	 * @param cartDto The cart to clear
	 * @return The now empty cart.
	 */
	/*@DeleteMapping(value = { "/clearCart", "/clearCart/"})
	public CartDto clearCart(@RequestBody CartDto cartDto) {
		Cart cart = cartService.getCart(cartDto.getCustomer().getUsername());
		return ControllerUtils.convertToDto(cartService.clearCart(cart));
	}
	*/
}
