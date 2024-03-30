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
import ca.mcgill.ecse321.backend.model.Artwork;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Customer;


@ExtendWith(MockitoExtension.class)
public class TestBackendServiceCart {

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
    private ManagerService managerService;
    @InjectMocks
    private CartService cartService;
    

    private static final String CUSTOMER_KEY = "TestCustomer";
    private static final String ARTWORK_KEY = "TestArtwork";
    private static final String NON_EXISTING_USER_KEY = "DoNotExist";
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
        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(customerRepo.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(cartRepo.save(any(Cart.class))).thenAnswer(returnParameterAsAnswer);
 
    }
    
 
     
     @Test
     public void testGetExistingCart() {
         assertEquals(CART_KEY, cartService.getCart(CART_KEY).getId());
     }
     
     @Test
     public void testGetExistingCartNullID() {
         Integer i = null;
         @SuppressWarnings("unused")
		Cart cart = new Cart();
         String error = "";
         try {
             cart = cartService.getCart(i);
         }
         catch(IllegalArgumentException e){
             error=e.getMessage();
         }
         assertEquals("cartID cannot be null!",error);
     }
     
     @Test
     public void testGetExistingCartNullCustomer() {
         Customer i=null;
         String error="";
         try {
             cartService.getCart(i);
         }
         catch(IllegalArgumentException e){
             error=e.getMessage();
         }
         assertEquals("Customer needs to be selected for cart!",error);
     }
     
     @Test
     public void testGetNonExistingCartCustomer() {
         Customer i=customerRepo.findCustomerByUsername(NON_EXISTING_USER_KEY);
         String error="";
         try {
             cartService.getCart(i);
         }
         catch(IllegalArgumentException e){
             error=e.getMessage();
         }
         assertEquals("Customer needs to be selected for cart!",error);
     }
     

     
     @Test
     public void testGetNonExistingCartByID() {
    	 String error = null;
    	 try {
    		 @SuppressWarnings("unused")
			Cart cart = cartService.getCart(NON_EXISTING_ID_KEY);
    	 } catch (IllegalArgumentException e) {
    		 error = e.getMessage();
    	 }
    	 assertEquals("No cart with given cartID exists!", error);
     }
     
     
     @Test
     public void testGetExistingCartByCustomer() {
         assertNotNull(customerRepo.findCustomerByUsername(CUSTOMER_KEY));
     }
     
     @Test
     public void testCreateCart() {
         assertEquals(0, managerService.getAllCarts().size());
         Cart cart = new Cart();
         try {
             cart = cartService.createCart(customerRepo.findCustomerByUsername(CUSTOMER_KEY));
         } catch (IllegalArgumentException e) {
             // Check that no error occurred
             fail();
         }
         assertNotNull(cart);
     }
     
     @Test
     public void testCreateCartNullCustomer() {
         assertEquals(0, managerService.getAllCarts().size());
         Customer customer = customerRepo.findCustomerByUsername(NON_EXISTING_USER_KEY);
         Cart cart = null;
         String error="";
         try {
             cart = cartService.createCart(customer);
         } catch (IllegalArgumentException e) {
             // Check that no error occurred
             error = e.getMessage();
         }
         assertNull(cart);
         assertEquals("Customer needs to be selected for cart!", error);
     }
     
     @Test
     public void testAddToCart() {
         Cart cart = cartService.addCartArtwork(CART_KEY, ARTWORK_KEY);
         assertEquals(2,cart.getArtwork().size());
     }
     
     @Test
     public void testAddToNonExistingCart() {
    	 String error = "";
    	 try {
              @SuppressWarnings("unused")
			Cart cart = cartService.addCartArtwork(NON_EXISTING_ID_KEY, ARTWORK_KEY);
    	 } catch (IllegalArgumentException e) {
    		 error = e.getMessage();
    	 }
    	 assertEquals("No cart with the given cartID exists!", error);
         
     }
     
     @Test
     public void testAddNonExistingArtworkToExistingCart() {
    	 String error = "";
    	 try {
              @SuppressWarnings("unused")
			Cart cart = cartService.removeCartArtwork(CART_KEY, NON_EXISTING_USER_KEY);
    	 } catch (IllegalArgumentException e) {
    		 error = e.getMessage();
    	 }
    	 assertEquals("No artwork with the given artName exists!", error); 
     }
     
     @Test
     public void testRemoveFromCart() {
         Cart cart = cartService.removeCartArtwork(CART_KEY, ARTWORK_KEY);
         assertEquals(0,cart.getArtwork().size());
     }
     
     @Test
     public void testRemoveFromNonExistingCart() {
    	 String error = "";
    	 try {
              @SuppressWarnings("unused")
			Cart cart = cartService.removeCartArtwork(NON_EXISTING_ID_KEY, ARTWORK_KEY);
    	 } catch (IllegalArgumentException e) {
    		 error = e.getMessage();
    	 }
    	 assertEquals("No cart with the given cartID exists!", error);
         
     }
     
     @Test
     public void testRemoveNonExistingArtworkToExistingCart() {
    	 String error = "";
    	 try {
              @SuppressWarnings("unused")
			Cart cart = cartService.removeCartArtwork(CART_KEY, NON_EXISTING_USER_KEY);
    	 } catch (IllegalArgumentException e) {
    		 error = e.getMessage();
    	 }
    	 assertEquals("No artwork with the given artName exists!", error); 
     }
     
     
     private int getId() {
         return allId++;
     }
}