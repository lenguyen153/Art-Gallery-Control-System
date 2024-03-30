package ca.mcgill.ecse321.backend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.backend.dto.ArtistDto;
import ca.mcgill.ecse321.backend.dto.TransactionDto;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Artwork;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Transaction;
import ca.mcgill.ecse321.backend.service.CartService;
import ca.mcgill.ecse321.backend.service.ManagerService;
import ca.mcgill.ecse321.backend.service.TransactionService;

@CrossOrigin(origins = "*")
@RestController
public class TransactionRestController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    CartService cartService;
    @Autowired
    ManagerService managerService;

    /**
     * Creates a transaction
     * @param transactionDto The transactionDto of the transaction to be created
     * @return The transactionDto of the created transaction
     */
    @PostMapping(value = {"/createTransaction", "/createTransaction/"})
    public TransactionDto createTransaction(@RequestBody TransactionDto transactionDto) {

        Transaction transaction = transactionService.createTransaction(
                        transactionDto.getPrice(),
                        transactionDto.getCommissionApplied(),
                        transactionDto.getTaxApplied(),
                        transactionDto.getDate(),
                        transactionDto.getArtistUsername(),
                        transactionDto.getCustomerUsername());
        return ControllerUtils.convertToDto(transaction);
    }

    /**
     * Updates a transaction.
     * @param transactionDto transactionDto of the transaction to be updated
     * @return The transactionDto of the updated transaction
     */
    @PostMapping(value = {"/updateTransaction", "/updateTransaction/"})
    public TransactionDto updateTransaction(@RequestBody TransactionDto transactionDto) {

        Transaction transaction = transactionService.updateTransaction(
                        transactionDto.getPrice(),
                        transactionDto.getId(),
                        transactionDto.getCommissionApplied(),
                        transactionDto.getTaxApplied(),
                        transactionDto.getDate(),
                        transactionDto.getArtistUsername(),
                        transactionDto.getCustomerUsername());
        return ControllerUtils.convertToDto(transaction);
    }
    
    /**
     * Retrieves a transaction.
     * @param id Id of the transaction to be retrieved
     * @return The retrieved transaction (null if not found)
     */
    @GetMapping(value = { "/getTransaction/{id}", "/getTransaction/{id}/" })
    public TransactionDto getTransactionById(@PathVariable Integer id) {
        Transaction transaction = transactionService.getTransactionByID(id);

        return ControllerUtils.convertToDto(transaction);
    }

    /**
     * 
     * @return A list containing all transactions
     */
    @GetMapping(value = { "/AllTransactions", "/AllTransactions/" })
    public List<TransactionDto> getAllTransactions() {
        List<Transaction> list = managerService.getAllTransactions();
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for(Transaction transaction : list) {
            transactionDtos.add(ControllerUtils.convertToDto(transaction));
        }

        return transactionDtos;
    }
    
    /**
     * Retrieves all the transactions associated with a particular user.
     * @param username The username of the user whose transactions should be retrieved
     * @return A list containing all the transactions associated with the user
     */
    @GetMapping(value = {"/Transactions/{username}","/Transactions/{username}/"})
    public List<TransactionDto> getTransactionByUsername(@PathVariable String username) {
        List<Transaction> list = transactionService.getTransactionByUsername(username);
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for(Transaction transaction : list) {
            transactionDtos.add(ControllerUtils.convertToDto(transaction));
        }
        return transactionDtos;
    }

    /**
     * Deletes a transaction
     * @param id The id of the transaction to be deleted
     * @return The transactionDto of the transaction that was deleted
     */
    @PostMapping(value = {"/deleteTransaction/{id}","/deleteTransaction/{id}/"})
    public TransactionDto deleteTransaction(@PathVariable Integer id) {
        Transaction transaction =transactionService.deleteTransaction(id);
        if(transaction != null) {
           return ControllerUtils.convertToDto(transaction);
        }
        return null;
    }
    
    /**
     * Creates a transaction for each item present in a customer's cart.
     * @param cartID The id of the cart for which the transactions should be created
     * @return A list which contains all the created transactions
     */
    @PostMapping(value = {"/createTransactionsFromCart/{cartID}", "/createTransactionsFromCart/{cartID}/"})
    public List<TransactionDto> createTransactionsFromCart(@PathVariable Integer cartID) {
        List<TransactionDto> list = new ArrayList<>();
        Cart cart = cartService.getCart(cartID);
        while(cart !=null && cartService.getCart(cartID).getArtwork().size() != 0) {
            //take the first artwork and make the transaction            
            Artwork artwork = cart.getArtwork().get(0);
            Artist artist =  artwork.getArtist();
            //if the artist has a specific commision, take it as commision, otherwise take the default one
            double commision = artist.getSpecificCommisionApplied() == null ? ArtistDto.getDefaultCommisionApplied() : artist.getSpecificCommisionApplied();
            double tax = TransactionDto.getTax();
            double preprice = artwork.getPrice();
            double price = preprice * tax + commision;
            Date date = new Date();
            Transaction transaction = transactionService.createTransaction(price, commision, tax,
                        date, artist.getUsername(), cart.getCustomer().getUsername());    
            
            list.add(ControllerUtils.convertToDto(transaction));          
            cart = cartService.removeCartArtwork(cartID, artwork);
        }
        cartService.updateCart(cart);
        return list;
    }
}