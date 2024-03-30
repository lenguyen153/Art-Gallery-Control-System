package ca.mcgill.ecse321.backend.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.backend.model.Artist;

public interface ArtistRepository extends CrudRepository <Artist, String> {
	
	Artist findArtistByUsername(String username);
	
	Artist findArtistByName(String name);
	
	Artist findArtistById(Integer id);
	
	Artist removeArtistByUsername(String username);
	
	Artist removeArtistById(Integer id);
	
	void deleteAll();

}
