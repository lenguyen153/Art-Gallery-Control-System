package ca.mcgill.ecse321.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ManyToMany;

@Entity
@Table(name = "customer_cart")
public class Cart {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	public void setId(Integer value) {
		this.id = value;
	}

	public Integer getId() {
		return this.id;
	}

	@OneToOne(optional = false)
	@JoinColumn(name = "customer")
	private Customer customer;

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToMany
	private List<Artwork> artwork = new ArrayList<>();

	public List<Artwork> getArtwork() {
		return this.artwork;
	}

	public void setArtwork(List<Artwork> artworks) {
		this.artwork = artworks;
	}
	
	public void addArtwork(Artwork artwork) {
	    this.artwork.add(artwork);
	}

}