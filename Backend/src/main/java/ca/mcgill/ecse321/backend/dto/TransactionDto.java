package ca.mcgill.ecse321.backend.dto;

import java.util.Date;

public class TransactionDto {
    private static Double tax = 1.0;
	private Double price;
	private Integer id;
	private double commissionApplied;
	private double taxApplied;
	private Date date;
	private String customerUsername;
	private String artistUsername;

	public TransactionDto() {
		
	}
	
	public TransactionDto(Double price, Integer id, double commission, double tax, Date date) {
		this.price = price;
		this.id = id;
		this.commissionApplied = commission;
		this.taxApplied = tax;
		this.date = date;
	}

	public void setPrice(Double value) {
		this.price = value;
	}
	public Double getPrice() {
		return this.price;
	}

	public void setCommissionApplied(double value) {
		this.commissionApplied = value;
	}
	
	public double getCommissionApplied() {
		return this.commissionApplied;
	}

	public void setTaxApplied(double value) {
		this.taxApplied = value;
	}
	public double getTaxApplied() {
		return this.taxApplied;
	}

	public void setDate(Date value) {
		this.date = value;
	}
	public Date getDate() {
		return this.date;
	}

	public void setId(Integer value) {
		this.id = value;
	}

	public Integer getId() {
		return this.id;
	}
	
	public String getCustomerUsername() {
	    return customerUsername;
	}
	
	public void setCustomerUsername(String customerUsername) {
	    this.customerUsername = customerUsername;
	}
	
	public String getArtistUsername() {
	    return artistUsername;
	}
	
	public void setArtistUsername(String artistUsername) {
        this.artistUsername = artistUsername;
    }
		
	public static void setTax(Double newTax) {
        tax = newTax;
     }
     
    public static Double getTax() {
         return tax;
    }
}
