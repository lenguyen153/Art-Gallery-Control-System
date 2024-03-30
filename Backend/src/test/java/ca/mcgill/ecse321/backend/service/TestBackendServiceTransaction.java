package ca.mcgill.ecse321.backend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ca.mcgill.ecse321.backend.dao.ArtistRepository;
import ca.mcgill.ecse321.backend.dao.ArtworkRepository;
import ca.mcgill.ecse321.backend.dao.CartRepository;
import ca.mcgill.ecse321.backend.dao.CustomerRepository;
import ca.mcgill.ecse321.backend.dao.ManagerRepository;
import ca.mcgill.ecse321.backend.dao.TransactionRepository;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Artwork;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Customer;
import ca.mcgill.ecse321.backend.model.Manager;
import ca.mcgill.ecse321.backend.model.Transaction;

@ExtendWith(MockitoExtension.class)
public class TestBackendServiceTransaction {

    @Mock
    private CustomerRepository customerRepo;
    @Mock
    private ArtistRepository artistRepo;
    @Mock
    private ManagerRepository managerRepo;
    @Mock
    private ArtworkRepository artworkRepo;
    @Mock
    private TransactionRepository transactionRepo;
    @Mock
    private CartRepository cartRepo;

    @InjectMocks
    private ManagerService managerService;

    @InjectMocks
    private TransactionService transactionService;


    private static final String CUSTOMER_KEY = "TestCustomer";
    private static final String ARTIST_KEY = "TestArtist";
    private static final String MANAGER_KEY = "TestManager";
    private static final String ARTWORK_KEY = "TestArtwork";
    private static final Integer TRANSACTION_KEY = 1;
    private static final Integer CART_KEY = 1;
    private static final Integer NON_EXISTING_ID_KEY = 12;
    private static int allId = 10;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(customerRepo.findCustomerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(CUSTOMER_KEY)) {
                Customer customer = new Customer();
                Cart cart = new Cart();
                customer.setUsername(CUSTOMER_KEY);
                customer.setProfile("I am a customer");
                customer.setCart(cart);
                customer.setId(getId());
                cart.setCustomer(customer);
                cart.setId(CART_KEY);
                return customer;
            } else {
                return null;
            }
        });
        
        lenient().when(artistRepo.findArtistByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ARTIST_KEY)) {
                Artist artist = new Artist();
                artist.setUsername(ARTIST_KEY);
                artist.setProfile("I am an artist");
                artist.setId(getId());
                Artwork artwork = new Artwork();
                artwork.setName(ARTWORK_KEY);
                artist.addArtwork(artwork);
                return artist;
            } else {
                return null;
            }
        });
        
        lenient().when(managerRepo.findManagerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(MANAGER_KEY)) {
                Manager manager = new Manager();
                manager.setUsername(MANAGER_KEY);
                manager.setProfile("I am a manager");
                manager.setId(getId());
                return manager;
            } else {
                return null;
            }
        });
        
        lenient().when(artworkRepo.findArtworkByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ARTWORK_KEY)) {
                Artwork artwork = new Artwork();
                artwork.setName(ARTWORK_KEY);
                artwork.setDescription("This is a test artwork");
                artwork.setNbOfCopy(5);
                artwork.setPrice(10);
                return artwork;
            } else {
                return null;
            }
        });
        
        lenient().when(transactionRepo.findTransactionById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0) == TRANSACTION_KEY) {
                Transaction transaction = new Transaction();
                transaction.setId(TRANSACTION_KEY);
                transaction.setArtist(artistRepo.findArtistByUsername(ARTIST_KEY));
                transaction.setCustomer(customerRepo.findCustomerByUsername(CUSTOMER_KEY));
                transaction.setPrice(69.0);
                return transaction;
            } else {
                return null;
            }
        });
        
        lenient().when(cartRepo.findCartById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0) == CART_KEY) {
                Cart cart = new Cart();
                cart.setId(CART_KEY);
                cart.setCustomer(customerRepo.findCustomerByUsername(CUSTOMER_KEY));
                List<Artwork>list = new ArrayList<Artwork>();
                list.add(artworkRepo.findArtworkByName(ARTWORK_KEY));
                cart.setArtwork(list);
                return cart;
            } else {
                return null;
            }
        });
        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(artistRepo.save(any(Artist.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(customerRepo.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(managerRepo.save(any(Manager.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(cartRepo.save(any(Cart.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(transactionRepo.save(any(Transaction.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(artworkRepo.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);
 
    }
    
    
    @Test
    public void testGetExistingTransactionByID() {
        assertEquals(TRANSACTION_KEY, transactionService.getTransactionByID(TRANSACTION_KEY).getId());
    }
    
    @Test
    public void testGetExistingTransactionByUsername() {
        assertNotNull(transactionService.getTransactionByUsername(customerRepo.findCustomerByUsername(CUSTOMER_KEY).getUsername()));
        assertNotNull(transactionService.getTransactionByUsername(artistRepo.findArtistByUsername(ARTIST_KEY).getUsername()));
    }
    
    @Test
    public void testGetNonExistingTransaction() {
        assertNull(transactionService.getTransactionByID(NON_EXISTING_ID_KEY));
    }
    
    @Test
    public void testCreateTransaction() {
        assertEquals(0, managerService.getAllTransactions().size());
        Transaction transaction = new Transaction();
        try {
            transaction = transactionService.createTransaction(10,11,12,new Date(),ARTIST_KEY,CUSTOMER_KEY);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            fail();
        }
        assertNotNull(transaction);
    }
    
    @Test
    public void testUpdateTransaction() {
        Transaction transaction = transactionRepo.findTransactionById(TRANSACTION_KEY);
        transaction = transactionService.updateTransaction(400, TRANSACTION_KEY, 400, 400, transaction.getDate(), 
                transaction.getArtist().getUsername(), transaction.getCustomer().getUsername());
        
        assertEquals(400, transaction.getPrice());
        assertEquals(TRANSACTION_KEY, transaction.getId());
    }
    
    @Test
    public void testUpdateNonExistingTransaction() {
        Transaction transaction = transactionService.updateTransaction(400, NON_EXISTING_ID_KEY, 400, 400, null, 
                null,null);
        assertNull(transaction);
    }
    
    @Test
    public void testUpdateExistingTransactionNullCustomerName() {
        Transaction transaction = transactionService.updateTransaction(400, TRANSACTION_KEY, 400, 400, null, 
                null,null);
        assertNull(transaction);
    }
    
    @Test
    public void testDeleteNonExistingTransaction() {      
        Transaction transaction = transactionService.deleteTransaction(NON_EXISTING_ID_KEY);  
        assertNull(transaction);
    }
    
    @Test
    public void testDeleteTransactionNullName() {
        String error = null;
        try {
            transactionService.deleteTransaction(null);
        }
        catch(IllegalArgumentException err) {
            error = err.getMessage();
        }
        assertEquals("Transaction name cannot be empty!",error);
    }
    
    @Test
    public void testDeleteExistingTransaction() {
        Transaction transaction = transactionService.deleteTransaction(TRANSACTION_KEY);  
        assertNotNull(transaction);
    }
    
    private int getId() {
        return allId++;
    }
}
