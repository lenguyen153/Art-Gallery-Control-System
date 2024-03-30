package ca.mcgill.ecse321.backend.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Artwork{
	@Id
	private String name;

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}
	private String description;

	public void setDescription(String value) {
		this.description = value;
	}
	public String getDescription() {
		return this.description;
	}
	
	@Enumerated
	private ArtStyle artStyle;

	public void setArtStyle(ArtStyle value) {
		this.artStyle = value;
	}
	public ArtStyle getArtStyle() {
		return this.artStyle;
	}
	private Integer price;

	public void setPrice(Integer value) {
		this.price = value;
	}
	public Integer getPrice() {
		return this.price;
	}
	private Integer nbOfCopy;

	public void setNbOfCopy(Integer value) {
		this.nbOfCopy = value;
	}
	public Integer getNbOfCopy() {
		return this.nbOfCopy;
	}

	@ManyToOne(optional=false)
	private Artist artist;

	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
	@Override
    public boolean equals(Object o) {
      if (o instanceof Artwork) {
          Artwork t = (Artwork) o;
        //technically, only id could be used.  
        if (t.getName().equals(this.getName())) {
            return true;            
        }   
      }
      return false;
    }

}