package ca.mcgill.ecse321.backend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import java.util.ArrayList;
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
public class TestBackendServiceManager {

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
    private CustomerService customerService;
    @InjectMocks
    private ArtistService artistService;
    @InjectMocks
    private ManagerService managerService;
    @InjectMocks
    private ArtworkService artworkService;
    @InjectMocks
    private TransactionService transactionService;
    @InjectMocks
    private CartService cartService;
    @InjectMocks
    private UserService userService;

    private static final String CUSTOMER_KEY = "TestCustomer";
    private static final String ARTIST_KEY = "TestArtist";
    private static final String MANAGER_KEY = "TestManager";
    private static final String ARTWORK_KEY = "TestArtwork";
    private static final String NON_EXISTING_USER_KEY = "DoNotExist";
    private static final Integer TRANSACTION_KEY = 1;
    private static final Integer CART_KEY = 1;
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
                customer.setIsBanned(false);
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
                artist.setIsBanned(false);
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
                manager.setIsBanned(false);
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
    public void testCreateManager() {
        assertEquals(0, managerService.getAllManagers().size());

        String username = "David";
        Manager manager = null;
        try {
            manager = managerService.createManager("name1", username, "pass1");
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            fail();
        }
        assertNotNull(manager);
        assertEquals(username, manager.getUsername());
    }

    @Test
    public void testGetExistingManager() {
        assertEquals(MANAGER_KEY, managerService.getManager(MANAGER_KEY).getUsername());
    }

    @Test
    public void testGetNonExistingManager() {
        assertNull(managerService.getManager(NON_EXISTING_USER_KEY));
    }

    @Test
    public void testCreateManagerNullUsernameAndPassword() {
        String name = null;
        String error = null;
        Manager manager = null;

        try {
            manager = managerService.createManager(name, name, name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(manager);
        // check error
        assertEquals("username cannot be empty password cannot be empty name cannot be empty", error);
    }

    private int getId() {
        return allId++;
    }

    @Test
    public void testFindAllManager() {
        List<Manager> list = managerService.getAllManagers();
        assertNotNull(list);
    }

    @Test
    public void testFindAllCustomer() {
        List<Customer> list = managerService.getAllCustomers();
        assertNotNull(list);
    }

    @Test
    public void testFindAllArtist() {
        List<Artist> list = managerService.getAllArtists();
        assertNotNull(list);
    }

    @Test
    public void testFindAllTransaction() {
        List<Transaction> list = managerService.getAllTransactions();
        assertNotNull(list);
    }

    @Test
    public void testFindAllCart() {
        List<Cart> list = managerService.getAllCarts();
        assertNotNull(list);
    }
    
    @Test
    public void testBanUserArtist() {
        assertNotNull(userService.banUser(ARTIST_KEY));
    }
    
    @Test
    public void testBanUserCustomer() {
        assertNotNull(userService.banUser(CUSTOMER_KEY));
    }
    
    @Test
    public void testBanUserNonExistingArtist() {
        assertNull(userService.banUser(NON_EXISTING_USER_KEY));
    }
    
    @Test
    public void testBanUserNonExistingCustomer() {
        assertNull(userService.banUser(NON_EXISTING_USER_KEY));
    }
    
    @Test
    public void testunbanUserArtist() {
        assertNotNull(userService.unbanUser(ARTIST_KEY));
    }
    
    @Test
    public void testunbanUserCustomer() {
        assertNotNull(userService.unbanUser(CUSTOMER_KEY));
    }
    
    @Test
    public void testUnbanUserNonExistingArtist() {
        assertNull(userService.unbanUser(NON_EXISTING_USER_KEY));
    }
    
    @Test
    public void testUnbanUserNonExistingCustomer() {
        assertNull(userService.unbanUser(NON_EXISTING_USER_KEY));
    }
    
    @Test
    public void testGetIsBannedArtist() {
        assertNotNull(userService.getIsBan(ARTIST_KEY)); 
    }
    
    @Test
    public void testGetIsBannedCustomer() {
        assertNotNull(userService.getIsBan(CUSTOMER_KEY));
    }
    
    @Test
    public void testGetIsBannedNonExistingArtist() {
        assertNull(userService.getIsBan(NON_EXISTING_USER_KEY)); 
    }
    
    @Test
    public void testGetIsBannedNonExistingCustomer() {
        assertNull(userService.getIsBan(NON_EXISTING_USER_KEY));
    }
    
    
    @Test
    public void testDeleteNonExistingManager() {      
        Manager manager = managerService.deleteManager(NON_EXISTING_USER_KEY);  
        assertNull(manager);
    }
    
    @Test
    public void testDeleteManagerNullName() {
        String error = null;
        try {
            managerService.deleteManager(null);
        }
        catch(IllegalArgumentException err) {
            error = err.getMessage();
        }
        assertEquals("Manager name cannot be empty!",error);
    }
    
    @Test
    public void testUpdateUserArtist() {
        Artist artist = (Artist)userService.updateUser(ARTIST_KEY, ARTIST_KEY, ARTIST_KEY);
        assertEquals("TestArtist",artist.getUsername());
        assertEquals("TestArtist",artist.getName());
    }
    
    @Test
    public void testUpdateNonExistingUserArtist() {
        assertNull(userService.updateUser(ARTIST_KEY, NON_EXISTING_USER_KEY, ARTIST_KEY));
    }
    
    @Test
    public void testUpdateUserManager() {
        Manager manager = (Manager)userService.updateUser(MANAGER_KEY, MANAGER_KEY, MANAGER_KEY);
        assertEquals("TestManager",manager.getUsername());
        assertEquals("TestManager",manager.getName());
    }
    
    @Test
    public void testUpdateNonExistingUserManager() {
        assertNull(userService.updateUser(MANAGER_KEY, NON_EXISTING_USER_KEY, MANAGER_KEY));
    }
    
    @Test
    public void testUpdateUserCustomer() {
        Customer customer = (Customer)userService.updateUser(CUSTOMER_KEY, CUSTOMER_KEY, CUSTOMER_KEY);
        assertEquals("TestCustomer",customer.getUsername());
        assertEquals("TestCustomer",customer.getName());
    }
    
    @Test
    public void testUpdateNonExistingUserCustomer() {
        assertNull(userService.updateUser(CUSTOMER_KEY, NON_EXISTING_USER_KEY, CUSTOMER_KEY));
    }
    
    @Test
    public void testDeleteExistingManager() {
        Manager manager = managerService.deleteManager(MANAGER_KEY);  
        assertNotNull(manager);
    }

}
