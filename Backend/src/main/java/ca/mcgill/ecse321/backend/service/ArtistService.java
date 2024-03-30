package ca.mcgill.ecse321.backend.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.backend.dao.ArtistRepository;
import ca.mcgill.ecse321.backend.dao.CustomerRepository;
import ca.mcgill.ecse321.backend.dao.TransactionRepository;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Artwork;
import ca.mcgill.ecse321.backend.model.Transaction;

@Service
public class ArtistService {

    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CustomerRepository customerRepository;

    /**
     * Creates a new artist (adds it to the database).
     * @param name The name of the artist
     * @param username The username of the artist
     * @param password The password of the artist
     * @return The created artist
     */
    @Transactional
    public Artist createArtist(String name, String username, String password) {
        StringBuilder error = new StringBuilder();
        if (username == null) {
            error.append("username cannot be empty ");
        }
        if (password == null) {
            error.append("password cannot be empty ");
        }

        if (name == null) {
            error.append("name cannot be empty");
        }
        if (error.length() > 0) {
            throw new IllegalArgumentException(error.toString());
        }
        if (artistRepository.findArtistByUsername(username) != null) {
            throw new IllegalArgumentException("Artist already exists");
        }
        Artist artist = new Artist();
        artist.setName(name);
        artist.setUsername(username);
        artist.setPassword(password);
        List<Artwork> artworks = new ArrayList<>();
        artist.setArtwork(artworks);
        return artistRepository.save(artist);
    }

    /**
     * Returns the artist with the given username
     * @param name The username of the artist
     * @return The artist or null if he does not exist
     */
    @Transactional
    public Artist getArtist(String name) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Artist name cannot be empty!");
        }
        return artistRepository.findArtistByUsername(name);
    }
    
    /**
     * 
     * @return All artists
     */
	@Transactional
	public List<Artist> getAllArtists() {
		return ServiceUtils.toList(artistRepository.findAll());
	}
    
    @Transactional
    public Double updateDefaultCommision(Double commision) {
        if(commision != null && commision >= 0) {
        Artist.setCommisionApplied(commision);
        return Artist.getDefaultCommisionApplied();
        }
        return null;
    }
    
    /**
     * Updates the commision for a specific artist
     * @param username The username of the artist
     * @param specificCommision The new commision of the artist
     * @return
     */
    @Transactional
    public Artist updateCommisionArtist(String username, Double specificCommision) {
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
     * Updates the shipToGallery boolean of the artist
     * @param username The username of the artist
     * @param shipToGallery the new boolean value
     * @return The saved artist (with the new boolean value)
     */
    @Transactional
    public Artist updateShipmentArtist(String username, Boolean shipToGallery) {
    	String error = "";
        if (username == null || username.trim().length() == 0) {
            error = error + "Artist name cannot be empty!";
        }
        if (shipToGallery == null) {
        	error = error + "A shipping option must be specified! ";
        }
        
        error = error.trim();
        
        if (error.length() > 0) {
        	throw new IllegalArgumentException(error);
        }
        Artist artist = null;
        if ((artist = artistRepository.findArtistByUsername(username)) != null) {
            artist.setShipToGallery(shipToGallery);
            return artistRepository.save(artist);
            
        }
        
        return null;
    }

    /**
     * Deletes the artist with the username passed as parameter
     * @param username The username of the artist to be deleted
     * @return The artist that was deleted or null if he did not exist
     */
    @Transactional
    public Artist deleteArtist(String username) {
        if (username == null || username.trim().length() == 0 || username == null) {
            throw new IllegalArgumentException("Artist name cannot be empty!");
        }
        Artist artist = null;
        if ((artist = artistRepository.findArtistByUsername(username)) != null) {
            List<Transaction> list = artist.getTransaction();
            for (int i = 0; i < list.size(); i++) {
                // remove from customer list
                Transaction transaction = list.get(i);
                transaction.getCustomer().getTransaction().remove(transaction);
                customerRepository.save(transaction.getCustomer());
                transactionRepository.delete(transaction);
            }
            artistRepository.delete(artist);
            return artist;
        }
        return null;
    }

}
