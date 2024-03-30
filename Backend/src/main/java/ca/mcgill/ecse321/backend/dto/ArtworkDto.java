package ca.mcgill.ecse321.backend.dto;


public class ArtworkDto {
	
	// class variables
    private String artistUsername;
	private String name;
	private String description;
	private String artstyle;
	private int nbCopy;
	private int price;

	
	public ArtworkDto() {
		
	}
	
	public ArtworkDto(String artistUsername, String name, String description, String artstyle, int nbCopy, int price) {
		this.artistUsername = artistUsername;
		this.name = name;
		this.description = description;
		this.artstyle = artstyle;
		this.nbCopy = nbCopy;
		this.price = price;
	}
	
	public String getArtistUsername() {
		return this.artistUsername;
	}
	
	public void setArtistUsername(String artistUsername) {
		this.artistUsername = artistUsername;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getArtStyle() {
		return this.artstyle;
	}
	
	public void setArtstyle(String artstyle) {
		this.artstyle = artstyle;
	}
	
	public int getNbCopy() {
		return this.nbCopy;
	}
	
	public void setNbCopy(int nbCopy) {
		this.nbCopy = nbCopy;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
}


