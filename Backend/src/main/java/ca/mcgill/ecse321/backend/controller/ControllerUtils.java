package ca.mcgill.ecse321.backend.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.backend.dto.ArtistDto;
import ca.mcgill.ecse321.backend.dto.ArtworkDto;
import ca.mcgill.ecse321.backend.dto.CartDto;
import ca.mcgill.ecse321.backend.dto.CustomerDto;
import ca.mcgill.ecse321.backend.dto.ManagerDto;
import ca.mcgill.ecse321.backend.dto.TransactionDto;
import ca.mcgill.ecse321.backend.dto.UserDto;
import ca.mcgill.ecse321.backend.model.ArtStyle;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Artwork;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Customer;
import ca.mcgill.ecse321.backend.model.Manager;
import ca.mcgill.ecse321.backend.model.Transaction;
import ca.mcgill.ecse321.backend.model.User;


public class ControllerUtils {
//	public static ManagerDto convertToDto(Manager m) {
//		if (m == null) {
//			throw new IllegalArgumentException("There is no such manager!");
//		}
//		ManagerDto ManagerDto = new ManagerDto(m.getName());
//		ManagerDto.setEvents(createEventDtosForManager(m));
//		return ManagerDto;
//	}
	/**
	 * Convert an artist object to an artistDto object
	 * @param artist the artist object to be converted to dto
	 * @return The artistDto object
	 */
	public static ArtistDto convertToDto(Artist artist) {
		if (artist == null) {
			throw new IllegalArgumentException("There is no such artist!");
		}

		ArtistDto artistDto = new ArtistDto(artist.getName(), artist.getUsername(),
				 artist.getPassword(), artist.getId(), null);
		
		List<Artwork> artworks = artist.getArtwork();
		List<ArtworkDto> artworkDtos = new ArrayList<ArtworkDto>();
		
		if(artworks != null) {
			for(Artwork artwork : artworks) {
				artworkDtos.add(convertToDto(artwork));
			}
		}
				
		List<Transaction> transactions = artist.getTransaction();
        List<TransactionDto> transactionDtos = new ArrayList<TransactionDto>();
        
		if(transactions != null) {
            for(Transaction t : transactions) {
                transactionDtos.add(convertToDto(t));
            }
        }
        artistDto.setTransactions(transactionDtos);
		artistDto.setArtworks(artworkDtos);
		artistDto.setIsBanned(artist.getIsBanned());
		artistDto.setShipToGallery(artist.isShipToGallery());
		artistDto.setSpecificCommisionApplied(artist.getSpecificCommisionApplied());
		artistDto.setProfile(artist.getProfile());
		return artistDto;
	}
	
	/**
     * Convert an manager object to an managerDto object
     * @param manager the manager object to be converted to dto
     * @return The managerDto object
     */
	public static ManagerDto convertToDto(Manager manager) {
		if (manager == null) {
			throw new IllegalArgumentException("There is no such manager!");
		}
		
		ManagerDto managerDto = new ManagerDto(manager.getName(), manager.getUsername(),
				 manager.getPassword(), manager.getId());
		managerDto.setIsBanned(manager.getIsBanned());
		managerDto.setProfile(manager.getProfile());
		return managerDto;
	}
	
	/**
     * Convert an customer object to an customerDto object
     * @param customer the customer object to be converted to dto
     * @return The customerDto object
     */
	public static CustomerDto convertToDto(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("There is no such customer!");
		}
		
		CustomerDto customerDto = new CustomerDto(customer.getName(), customer.getUsername(),
		        customer.getPassword(), customer.getAddress(), customer.getId());
		customerDto.setIsBanned(customer.getIsBanned());
		
		List<Transaction> transactions = customer.getTransaction();
        List<TransactionDto> transactionDtos = new ArrayList<TransactionDto>();
        
        if(transactions != null) {
            for(Transaction t : transactions) {
                transactionDtos.add(convertToDto(t));
            }
        }
        customerDto.setTransactions(transactionDtos);
        customerDto.setCartDto(convertToDto(customer.getCart()));
        customerDto.setProfile(customer.getProfile());
		return customerDto;
	}
	
	/**
     * Convert an user object to an userDto object
     * @param user the user object to be converted to dto
     * @return The userDto object
     */
	public static UserDto converToDto(User user) {
	    if (user == null) {
            throw new IllegalArgumentException("There is no such user!");
        }
        
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        return userDto;
	}
	
	/**
     * Convert an cart object to an cartDto object
     * @param cart the cart object to be converted to dto
     * @return The cartDto object
     */
	public static CartDto convertToDto(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("There is no such cart!");
        }
        
        CartDto cartDto = new CartDto();
        cartDto.setCustomerUsername(cart.getCustomer().getUsername());
        
        List<Artwork> artworks = cart.getArtwork();
        List<ArtworkDto> artworkDtos = new ArrayList<ArtworkDto>();
        if(artworks != null) {
            for(Artwork artwork : artworks) {
                ArtworkDto artworkDto = convertToDto(artwork);
                artworkDtos.add(artworkDto);
            }
        }
        
        cartDto.setArtworks(artworkDtos);
        cartDto.setID(cart.getId());
        return cartDto;
    }
	
	/**
     * Convert an transaction object to an transactionDto object
     * @param transaction the transaction object to be converted to dto
     * @return The transactionDto object
     */
	public static TransactionDto convertToDto(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("There is no such customer!");
        }
        
        TransactionDto transactionDto = new TransactionDto(
                transaction.getPrice(),
                transaction.getId(),
                transaction.getCommissionApplied(),
                transaction.getTaxApplied(),
                transaction.getDate());
        transactionDto.setArtistUsername(transaction.getArtist().getUsername());
        transactionDto.setCustomerUsername(transaction.getCustomer().getUsername());
               
        return transactionDto;
    }
	
	/**
     * Convert an artwork object to an artworkDto object
     * @param artwork the artwork object to be converted to dto
     * @return The artworkDto object
     */
	public static ArtworkDto convertToDto(Artwork artwork) {
        if (artwork == null) {
            throw new IllegalArgumentException("There is no such artwork!");
        }
        ArtworkDto artworkDto = new ArtworkDto(
                artwork.getArtist().getUsername(),
                artwork.getName(),
                artwork.getDescription(),
                artwork.getArtStyle().name(),
                artwork.getNbOfCopy(),
                artwork.getPrice());
             
        return artworkDto;
    }
	
	/**
	 * Converts a string to an artstyle.
	 * @param artstyle The string to be converted to an artstyle
	 * @return The artstyle that corresponds to the string (default value is abstract)
	 */
	public static ArtStyle toArtStyle(String artstyle) {
	    artstyle = artstyle.toUpperCase();
	    switch(artstyle) {
	    case "ABSTRACT":  return ArtStyle.ABSTRACT;
	    case "REALISM":return ArtStyle.REALISM;
	    case "PHOTOREALISM":return ArtStyle.PHOTOREALISM;
        case "IMPRESSIONISM":return ArtStyle.IMPRESSIONISM;
        case "SURREALISM":return ArtStyle.SURREALISM;
        case "POP":return ArtStyle.POP;
        case "ART_NOUVEAU":return ArtStyle.ART_NOUVEAU;
        case "MODERNISM":return ArtStyle.MODERNISM;
        case "SCULPTURE":return ArtStyle.SCULPTURE;
        case "MODELING":return ArtStyle.MODELING;        
	    default: return ArtStyle.ABSTRACT;   
	    }
	}

}
