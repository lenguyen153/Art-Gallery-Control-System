package ca.mcgill.ecse321.backend.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.backend.model.Artwork;

public interface ArtworkRepository extends CrudRepository<Artwork, String> {

	Artwork findArtworkByName(String name);
		
	Artwork removeArtworkByName(String name);
	
	void deleteAll();
}
