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

import ca.mcgill.ecse321.backend.dto.ArtworkDto;
import ca.mcgill.ecse321.backend.model.Artwork;
import ca.mcgill.ecse321.backend.service.ArtworkService;

@CrossOrigin(origins = "*")
@RestController
public class ArtworkRestController {
    @Autowired
    private ArtworkService artworkService;
    
    /**
     * Retrieves an artwork.
     * @param name The name of the artwork to be retrieved
     * @return The artworkDto of the artwork retrieved
     */
    @GetMapping(value = {"/getArtwork/{name}", "/getArtwork/{name}/"})
    public ArtworkDto getArtwork(@PathVariable String name) {
        if(artworkService.getArtwork(name)!=null) {
            return ControllerUtils.convertToDto(artworkService.getArtwork(name));
            }
            return null;
    }

    /**
     * 
     * @return A list containing all artworks
     */
    @GetMapping(value = { "/AllArtworks", "/AllArtworks/" })
    public List<ArtworkDto> getAllArtworks() {
        List<ArtworkDto> artworksDtos = new ArrayList<>();
        List<Artwork> artworks = artworkService.getAllArtworks();
        for(Artwork artwork : artworks) {
            artworksDtos.add(ControllerUtils.convertToDto(artwork));
        }

        return artworksDtos;
    }
    
    /**
     * Retrieves all the artworks of a specific style.
     * @param artstyle The artstyle of artworks to be retrieved
     * @return A list containing the artworks of the specified artstyle
     */
    @GetMapping(value = { "/AllArtworks/{artstyle}", "/AllArtworks/{artstyle}/" })
    public List<ArtworkDto> getAllArtworksByStyle(@PathVariable String artstyle) {
//        ArtStyle artStyle = ControllerUtils.toArtStyle(artstyle);
        List<ArtworkDto> artworksDtos = new ArrayList<>();
        List<Artwork> artworks = artworkService.getArtworkFromStyle(artstyle);
        for(Artwork artwork : artworks) {
               artworksDtos.add(ControllerUtils.convertToDto(artwork));
        }
        return artworksDtos;
    }
    
    /**
     * Retrieves all the artworks of an artist by name.
     * @param name The name of the artist. 
     * @return A list containing the artworks of the required artist
     */
	@GetMapping(value = { "/AllArtworksFromArtist/{username}", "/AllArtworksFromArtist/{username}/" })
	public List<ArtworkDto> getArtworkFromArtist(@PathVariable String username) {
        List<ArtworkDto> artworksDtos = new ArrayList<>();
        List<Artwork> artworks = artworkService.getAllArtworks();
        for(Artwork artwork : artworks) {
        	if(artwork.getArtist().getUsername().equals(username)){
               artworksDtos.add(ControllerUtils.convertToDto(artwork));
        	}
        }
        return artworksDtos;
	}

    /**
     * Creates an artwork for a user.
     * @param artworkDto The artworkDto of the artwork to be created
     * @param artistUsername The username of the artist which is creating an artwork
     * @return
     */
    @PostMapping(value = { "/createArtwork/{artistUsername}", "/createArtwork/{artistUsername}/" })
    public ArtworkDto createArtwork(@RequestBody ArtworkDto artworkDto, @PathVariable String artistUsername) {
        String artworkName = artworkDto.getName().replace('-', ' ');
        Artwork artwork = artworkService.createArtwork(artistUsername, 
                artworkName, artworkDto.getDescription(), artworkDto.getArtStyle(),artworkDto.getNbCopy(), artworkDto.getPrice());

        return ControllerUtils.convertToDto(artwork);
    }
    
    /**
     * Updates an artwork.
     * @param name Name of the artwork to be updated
     * @param description new description of the artwork
     * @param artstyle new artstyle of the artwork
     * @param nbcopy new number of copies of the artwork
     * @param price new price of the artwork
     * @return artworkDto of the updated artwork
     */
    @PutMapping(value = { "/updateArtwork", "/updateArtwork/" })
    public ArtworkDto updateArtwork(@RequestParam String name, @RequestParam String description,
                                        @RequestParam String artstyle, @RequestParam int nbcopy, @RequestParam int price) {
        String artworkName = name.replace('-', ' ');
        Artwork artwork = artworkService.updateArtwork(artworkName, description, artstyle, nbcopy, price);

        return ControllerUtils.convertToDto(artwork); //throws error if artwork doesn't exist
    }

    /**
     * Deletes an artwork.
     * @param name Name of the artwork to be deleted
     * @return artworkDto of the deleted artwork (null if no artwork with the name was found)
     */
    @DeleteMapping(value = { "/deleteArtwork/{name}", "/deleteArtwork/{name}/" })
    public ArtworkDto deleteArtwork(@PathVariable String name) {
       
        name = name.replace('-', ' '); //replace - by spaces (no space in url so replace with #)
        Artwork artwork = artworkService.deleteArtwork(name);
        if(artwork != null) {
        return ControllerUtils.convertToDto(artwork);
        }
        return null;
      
    }
}
