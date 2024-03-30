package ca.mcgill.ecse321.backend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Customer extends User{
   private String address;

	public void setAddress(String value) {
		this.address = value;
	}

	public String getAddress() {
		return this.address;
	}

	@OneToOne(mappedBy = "customer", cascade = { CascadeType.ALL })
	private Cart cart;

	public Cart getCart() {
		return this.cart;
	}

	public void setCart(Cart cart) {
		cart.setCustomer(this);
		this.cart = cart;
	}

	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private List<Transaction> transaction = new ArrayList<Transaction>();

	public List<Transaction> getTransaction() {
		return this.transaction;
	}

	public void addTransaction(Transaction transaction) {
		if (transaction != null) {
			transaction.setCustomer(this);
			this.transaction.add(transaction);
		}
	}

	public void setTransaction(List<Transaction> transactions) {
		this.transaction = transactions;
	}

}
