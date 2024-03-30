package ca.mcgill.ecse321.backend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
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
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Artwork;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Customer;
import ca.mcgill.ecse321.backend.model.Manager;


@ExtendWith(MockitoExtension.class)
public class TestBackendServiceUser {

    @Mock
    private CustomerRepository customerRepo;
    @Mock
    private ArtistRepository artistRepo;
    @Mock
    private ManagerRepository managerRepo;
    @Mock
    private ArtworkRepository artworkRepo;
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
    private CartService cartService;
    @InjectMocks
    private UserService userService;

    private static final String CUSTOMER_KEY = "TestCustomer";
    private static final String ARTIST_KEY = "TestArtist";
    private static final String MANAGER_KEY = "TestManager";
    private static final String ARTWORK_KEY = "TestArtwork";
    private static final String NON_EXISTING_USER_KEY = "DoNotExist";
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
        lenient().when(artworkRepo.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);
 
    }

    @Test
    public void testGetProfileArtistUser() {
        assertEquals(0, managerService.getAllArtists().size());
        String s = userService.getProfile(ARTIST_KEY); 
        assertEquals("I am an artist",s);
    }
    
    @Test
    public void testGetProfileCustomerUser() {
        assertEquals(0, managerService.getAllCustomers().size());
        String s = userService.getProfile(CUSTOMER_KEY);
        assertEquals("I am a customer",s);
    }
    
    @Test
    public void testGetProfileManagerUser() {
        assertEquals(0, managerService.getAllManagers().size());
        String s = userService.getProfile(MANAGER_KEY);
        assertEquals("I am a manager",s);
    }
    
    @Test
    public void testGetProfileNullUser() {
        assertEquals(0, managerService.getAllManagers().size());
        String s = userService.getProfile(NON_EXISTING_USER_KEY);
        assertEquals("This user does not exist",s);
    }
    
    @Test
    public void testGetProfileNullName() {
        assertEquals(0, managerService.getAllManagers().size());
        String s = userService.getProfile(null);
        assertEquals("This name is null",s);
    }
    
    @Test
    public void testSetProfileArtistUser() {
        assertEquals(0, managerService.getAllArtists().size());
        String s = userService.setProfile(ARTIST_KEY,ARTIST_KEY); 
        assertEquals(ARTIST_KEY,s);
    }
    
    @Test
    public void testSetProfileManagerUser() {
        assertEquals(0, managerService.getAllManagers().size());
        String s = userService.setProfile(MANAGER_KEY,ARTIST_KEY); 
        assertEquals(ARTIST_KEY,s);
    }
    
    @Test
    public void testSetProfileCustomerUser() {
        assertEquals(0, managerService.getAllManagers().size());
        String s = userService.setProfile(CUSTOMER_KEY,ARTIST_KEY); 
        assertEquals(ARTIST_KEY,s);
    }
    
    @Test
    public void testSetProfileNullName() {
        assertEquals(0, managerService.getAllManagers().size());
        String s = userService.setProfile(null,ARTIST_KEY);
        assertEquals("This name is null",s);
    }
    
    
    @Test
    public void testSetProfileArtistUserNullProfile() {
        assertEquals(0, managerService.getAllArtists().size());
        String s = userService.setProfile(ARTIST_KEY,null); 
        assertEquals("This profile is empty(null)",s);
    }
    
    private int getId() {
        return allId++;
    }
    

}