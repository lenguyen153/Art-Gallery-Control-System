package ca.mcgill.ecse321.backend.dto;

import java.util.List;

public class CartDto {
	
	// class variables
	private String customerUsername;
	private List<ArtworkDto> artworks;
	private Integer id;
	
	public CartDto() {
		
	}
	
	public CartDto(String customerUsername, List<ArtworkDto> artworks) {
		this.customerUsername = customerUsername;
		this.artworks = artworks;
	}
	
	public String getCustomerUsername() {
		return this.customerUsername;
	}
	
	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}
	
	public List<ArtworkDto> getArtworks() {
		return this.artworks;
	}
	
	public void setArtworks(List<ArtworkDto> artworks) {
		this.artworks = artworks;
	}
	
	public Integer getID() {
	    return id;
	}
	
	public void setID(Integer id) {
	    this.id = id;
	}
	
	
	
	
	

	
	
	
	
	
}
