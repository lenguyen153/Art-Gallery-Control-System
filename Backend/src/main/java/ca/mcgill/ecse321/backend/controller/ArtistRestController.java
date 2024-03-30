package ca.mcgill.ecse321.backend.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.backend.dto.ArtistDto;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.service.ArtistService;


@CrossOrigin(origins = "*")
@RestController
public class ArtistRestController {    
    @Autowired
    private ArtistService artistService;
    
    /**
     * Creates an artist with the given artistDto.
     * @param artistDto The artistDto to be used to create an artist
     * @return The artistDto of the created artist
     */
    @PostMapping(value = { "/createArtist", "/createArtist/" })
    public ArtistDto createArtist(@RequestBody ArtistDto artistDto) {
        Artist artist = artistService.createArtist(
                        artistDto.getName(),
                        artistDto.getUsername(),
                        artistDto.getPassword());
        return ControllerUtils.convertToDto(artist);
    }
    
    /**
     * Updates the commision of the artist.
     * @param artistDto The artistDto of the artist which should have a new commision
     * @return The artistDto of the updated artist
     */
    @PostMapping(value = { "/updateCommissionArtist", "/updateCommissionArtist/" })
    public ArtistDto updateCommisionArtist(@RequestBody ArtistDto artistDto) {
        Artist artist = artistService.updateCommisionArtist( 
                        artistDto.getUsername(),
                        artistDto.getSpecificCommisionApplied());
        return ControllerUtils.convertToDto(artist);
    }
    
    /**
     * Updates the defaultCommision of the art gallery.
     * @param defaultCommision The new default commission
     * @return The new default commission
     */
    @PutMapping(value = { "/updateDefaultCommissionArtist", "/updateDefaultCommissionArtist/" })
    public Double updateDefaultCommisionArtist(@RequestParam Double defaultCommision) {
        return artistService.updateDefaultCommision(defaultCommision);
    }
    
    /**
     * Updates the shipToGallery boolean of an artist.
     * @param username The artist for which the boolean value should change
     * @param isShippedStr The new boolean value (as a non case sensitive string)
     * @return The artist which received the change
     */
    @PutMapping(value = { "/updateShipmentArtist", "/updateShipmentArtist/" })
    public ArtistDto updateShipmentArtist(@RequestParam String username, @RequestParam String isShippedStr) {
    	boolean isShipped;
    	if (isShippedStr.trim().equalsIgnoreCase("true")) {
    		isShipped = true;
    	} else {
    		isShipped = false;
    	}
    	
        Artist artist = artistService.updateShipmentArtist( 
                        username,
                        isShipped);
        return ControllerUtils.convertToDto(artist);
    }
    
    /**
     * Returns the artist with the given username.
     * @param name The username of the artist to be retrieved
     * @return The artistDto corresponding to the artist witht the parameter username
     */
    @GetMapping(value = {"/getArtist/{name}", "/getArtist/{name}/"})
    public ArtistDto getArtist(@PathVariable String name) {
        Artist artist = artistService.getArtist(name);   //throws error if string name is null/empty
        return ControllerUtils.convertToDto(artist); //throws error is artist is null (does not exist)
    }

    /**
     * Deletes the artist with the given username.
     * @param name The username of the artist to be retrieved
     * @return The artistDto corresponding to the artist with the parameter username
     */
    @DeleteMapping(value = {"/deleteArtist/{username}","/deleteArtist/{username}/"})
    public ArtistDto deleteArtist(@PathVariable String username) {
        Artist artist =artistService.deleteArtist(username);
        if(artist != null) {
            return ControllerUtils.convertToDto(artist);
        }
        return null;
    }
    
    /**
     * 
     * @return A list containing all the artists
     */
	@GetMapping(value = { "/AllArtists", "/AllArtists/" })
	public List<ArtistDto> getAllArtists() {
		List<ArtistDto> artistDtos = new ArrayList<>();
		List<Artist> artistList = artistService.getAllArtists();
		for(Artist artist : artistList) {
			artistDtos.add(ControllerUtils.convertToDto(artist));
		}

		return artistDtos;
	}
	

    /* @PostMapping(value = { "/persons/{name}", "/persons/{name}/" })
    public PersonDto createPerson(@PathVariable("name") String name) throws IllegalArgumentException {
        Person person = service.createPerson(name);
        return convertToDto(person);
    }*/
}
