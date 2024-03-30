package ca.mcgill.ecse321.backend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transaction{
    private static Double tax;
	private Double price;

	public void setPrice(Double value) {
		this.price = value;
	}
	public Double getPrice() {
		return this.price;
	}
	private double commissionApplied;

	public void setCommissionApplied(double value) {
		this.commissionApplied = value;
	}
	public double getCommissionApplied() {
		return this.commissionApplied;
	}
	private double taxApplied;

	public void setTaxApplied(double value) {
		this.taxApplied = value;
	}
	public double getTaxApplied() {
		return this.taxApplied;
	}
	private Date date;

	public void setDate(Date value) {
		this.date = value;
	}
	public Date getDate() {
		return this.date;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	public void setId(Integer value) {
		this.id = value;
	}

	public Integer getId() {
		return this.id;
	}


	@ManyToOne(optional=true)
	private Artist artist;

	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	@ManyToOne(optional=true)
	private Customer customer;

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public boolean equals(Object o) {
      if (o instanceof Transaction) {
          Transaction t = (Transaction) o;
        //technically, only id could be used.  
        if (t.getId().equals(this.getId())) {
            return true;            
        }   
      }
      return false;
	}
	
	public static void setTax(Double newTax) {
        tax = newTax;
     }
     
    public static Double getTax() {
         return tax;
    }

}
