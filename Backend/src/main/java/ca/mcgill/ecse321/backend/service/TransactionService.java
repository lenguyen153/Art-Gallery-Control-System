package ca.mcgill.ecse321.backend.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.backend.dao.ArtistRepository;
import ca.mcgill.ecse321.backend.dao.CustomerRepository;
import ca.mcgill.ecse321.backend.dao.ManagerRepository;
import ca.mcgill.ecse321.backend.dao.TransactionRepository;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Customer;
import ca.mcgill.ecse321.backend.model.Transaction;
import ca.mcgill.ecse321.backend.model.User;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    CustomerRepository customerRepository;

    /**
     * Creates a new transaction with the given parameters.
     * 
     * @param price The total price of the transaction
     * @param commission The commision taken on the transaction by the art gallery
     * @param tax The tax applied on the transaction
     * @param date The date the transaction happened
     * @param artistUsername The username of the artist who sold an artwork
     * @param customerUsername The username of the customer who bought an artwork
     * @return The created transaction
     */
    @Transactional
    public Transaction createTransaction(double price, double commission, double tax, Date date,String artistUsername,String customerUsername) {
        StringBuilder error = new StringBuilder();
        if (price < 0) {
            error.append("price is invalid");
        }
        if (commission < 0) {
            error.append("commission is invalid");
        }
        if (tax < 0) {
            error.append("tax is invalid");
        }

        Transaction transaction = new Transaction();
        transaction.setPrice(price);
        transaction.setCommissionApplied(commission);
        transaction.setTaxApplied(tax);
        transaction.setDate(date);
        transaction.setArtist(artistRepository.findArtistByUsername(artistUsername));
        transaction.setCustomer(customerRepository.findCustomerByUsername(customerUsername));
        artistRepository.save(transaction.getArtist());
        customerRepository.save(transaction.getCustomer());
        return transactionRepository.save(transaction);
    }
    
    /**
     * Updates a transaction with the given parameters.
     * 
     * @param price The total price of the transaction
     * @param commission The commision taken on the transaction by the art gallery
     * @param tax The tax applied on the transaction
     * @param date The date the transaction happened
     * @param artistUsername The username of the artist who sold an artwork
     * @param customerUsername The username of the customer who bought an artwork
     * @return The updated transaction
     */
    @Transactional
    public Transaction updateTransaction(double price, int id, double commission, double tax, Date date,String artistUsername,String customerUsername) {
        StringBuilder error = new StringBuilder();
        if (price < 0) {
            error.append("price is invalid");
        }
        if (commission < 0) {
            error.append("commission is invalid");
        }
        if (tax < 0) {
            error.append("tax is invalid");
        }

        Transaction transaction = transactionRepository.findTransactionById(id);
        if(transaction == null || artistUsername == null || customerUsername == null) {
            return null;
        }
        transaction.setPrice(price);
        transaction.setId(id);
        transaction.setCommissionApplied(commission);
        transaction.setTaxApplied(tax);
        transaction.setDate(date);
        transaction.setArtist(artistRepository.findArtistByUsername(artistUsername));
        transaction.setCustomer(customerRepository.findCustomerByUsername(customerUsername));
        return transactionRepository.save(transaction);
    }

    /**
     * Returns the transaction which has the given id.
     * @param id The id of the transaction to be retrieved
     * @return The transaction with the id or null if it does not exist
     */
    @Transactional
    public Transaction getTransactionByID(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Artwork name cannot be empty!");
        }
        return transactionRepository.findTransactionById(id);
    }
    
    /**
     * Returns all the transaction associated with a user (artist or customer only).
     * Throws an error if the user does not exist or is not an artist or customer.
     * 
     * @param username The username of the artist/manager
     * @return A list containing all the transactions of the user
     */
    @Transactional
    public List<Transaction> getTransactionByUsername(String username) {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Artwork name cannot be empty!");
        }
        User user =null;
        if((user = artistRepository.findArtistByUsername(username))!=null) {
            return ((Artist)user).getTransaction();
        }
        if((user = customerRepository.findCustomerByUsername(username))!=null) {
            return ((Customer)user).getTransaction();
        }        
        throw new IllegalArgumentException("The user is not a customer or an artist");     
    }
    
    /**
     * Deletes the transaction with the given id.
     * 
     * @param id The id of the transaction
     * @return The deleted transaction or null if it did not exist
     */
    @Transactional
    public Transaction deleteTransaction(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Transaction name cannot be empty!");
        }
       Transaction transaction = null;
       transaction = transactionRepository.findTransactionById(id);
       if(transaction != null) {
       Artist artist = transaction.getArtist();
       Customer customer = transaction.getCustomer();
       artist.getTransaction().remove(transaction);
       customer.getTransaction().remove(transaction);
       artistRepository.save(artist);
       customerRepository.save(customer);
       transactionRepository.delete(transaction);
       return transaction;
       }
       return null;
    }

}
