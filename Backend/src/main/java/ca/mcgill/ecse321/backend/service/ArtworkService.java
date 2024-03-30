package ca.mcgill.ecse321.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ca.mcgill.ecse321.backend.controller.ControllerUtils;
import ca.mcgill.ecse321.backend.dao.ArtistRepository;
import ca.mcgill.ecse321.backend.dao.ArtworkRepository;
import ca.mcgill.ecse321.backend.dto.ArtworkDto;
import ca.mcgill.ecse321.backend.model.ArtStyle;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Artwork;

@Service
public class ArtworkService {
	@Autowired
	ArtworkRepository artworkRepository;
	@Autowired
	ArtistRepository artistRepository;
	
	/**
	 * Creates a new artwork for the artist which has the given username (adds the artwork to the database).
	 * 
	 * @param artistUsername The username of the artist account
	 * @param name The name of the artwork
	 * @param description The description of the artwork
	 * @param artstyle The artstyle of the artwork (abstract by default or for invalid values)
	 * @param nbCopy The number of copy of the artwork
	 * @param price The price of the artwork
	 * @return
	 */
	@Transactional
	public Artwork createArtwork(String artistUsername, String name, String description, String artstyle, int nbCopy, int price) {
		String error = "";
		Artist artist = artistRepository.findArtistByUsername(artistUsername);
		if(artistRepository.findArtistByUsername(artistUsername) == null) {
			error = error + "Artist needs to be selected for artwork! ";
		} 

		if (name == null || name.trim().length() == 0 ) {
			error = error + "Artwork name cannot be empty! ";
		}
		if (description == null || description.trim().length() == 0) {
			error = error + "Artwork description cannot be empty! ";
		}
		if (artstyle == null) {
			error = error + "Artwork artstyle cannot be empty! ";
		}
		if (nbCopy < 1) {
			error = error + "Artwork number of copies cannot be less than 1! ";
		}
		if (price < 0) {
			error = error + "Artwork price cannot be negative!";
		}
		error = error.trim();
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		Artwork artwork = new Artwork();
		artwork.setArtist(artistRepository.findArtistByUsername(artistUsername));
		artwork.setName(name);
		artwork.setDescription(description);
		artwork.setArtStyle(ControllerUtils.toArtStyle(artstyle));
		artwork.setNbOfCopy(nbCopy);
		artwork.setPrice(price);
		artist.getArtwork().add(artwork);
		//will save the artwork by cascade
		artistRepository.save(artist);
		return artwork;	
	}
	

	/**
	 * Updates the artwork which has the given artwork name.
	 * @param name The artwork name
	 * @param description The artwork description
	 * @param artstyle The artstyle of the artwork
	 * @param nbCopy The number of copy of the artwork
	 * @param price The price of the artwork
	 * @return The updated artwork
	 */
	@Transactional
    public Artwork updateArtwork(String name, String description, String artstyle, int nbCopy, int price) {
		String error = "";
		if (name == null || name.trim().length() == 0) {
			error = error + "Artwork name cannot be empty! ";
		}
        if (description == null || description.trim().length() == 0) {
            error = error + "Artwork description cannot be empty! ";
        }
        if (artstyle == null || artstyle.trim().length() == 0) {
            error = error + "Artwork artstyle cannot be empty! ";
        }
        if (nbCopy < 1) {
            error = error + "Artwork number of copies cannot be less than 1! ";
        }
        if (price < 0) {
            error = error + "Artwork price cannot be negative!";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        Artwork artwork = artworkRepository.findArtworkByName(name);
        if(artwork == null) {
            return null;
        }
        artwork.setDescription(description);
        artwork.setArtStyle(ControllerUtils.toArtStyle(artstyle));
        artwork.setNbOfCopy(nbCopy);
        artwork.setPrice(price);
        artworkRepository.save(artwork);
        return artwork; 
    }
	
	/**
	 * Returns the artwork with the parameter name or null if it does not exist.
	 * @param name The name of the artwork
	 * @return The artwork with the name
	 */
	@Transactional
	public Artwork getArtwork(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("Artwork name cannot be empty!");
		}
		Artwork artwork = artworkRepository.findArtworkByName(name);
		return artwork;
	}	
	
	
	/**
	 * 
	 * @return All the artworks
	 */
	@Transactional
	public List<Artwork> getAllArtworks(){
		return ServiceUtils.toList(artworkRepository.findAll());
	}
	
	/**
	 * Returns the artwork with the parameter style or null if it does not exist.
	 * @param name The style of the artworks
	 * @return The artwork with the same style
	 */
	@Transactional
	public List<Artwork> getArtworkFromStyle(String style) {
		List<Artwork> artworks = new ArrayList<Artwork>();
		if (style.equals("ALL")) {
			return getAllArtworks();
		}else {
			for (Artwork a : artworkRepository.findAll()) {
				if(a.getArtStyle().equals(ServiceUtils.toArtStyle(style))) {
					artworks.add(a);
				}
			}
		}
		return artworks;
	}
	
    /**
     * Retrieves all the artworks of an artist by name.
     * @param name The name of the artist. 
     * @return A list containing the artworks of the required artist
     */
	@Transactional
	public List<Artwork> getArtworkFromArtist(String name) {
        List<Artwork> artworks = new ArrayList<>();
        for(Artwork a : artworkRepository.findAll()) {
        	if(a.getArtist().getUsername().equals(name)){
               artworks.add(a);
        	}
        }
        return artworks;
	}
	
	/**
	 * Deletes the artwork with the given name
	 * @param name The name of the artwork
	 * @return The deleted artwork or null if it did not exist
	 */
	@Transactional
	public Artwork deleteArtwork(String name) {
	    if (name == null || name.trim().length() == 0) {
	        throw new IllegalArgumentException("Artwork name cannot be empty!");
       	}
	    Artwork artwork = artworkRepository.findArtworkByName(name);

	    if(artwork !=null) {
	        Artist artist = artistRepository.findArtistByUsername(artwork.getArtist().getUsername());
	        artist.getArtwork().remove(artwork);
	        artistRepository.save(artist);
	        return artwork;
	    }
	    return null;
	}
}