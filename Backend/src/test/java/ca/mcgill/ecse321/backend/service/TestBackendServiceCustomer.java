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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ca.mcgill.ecse321.backend.dao.CartRepository;
import ca.mcgill.ecse321.backend.dao.CustomerRepository;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Customer;


@ExtendWith(MockitoExtension.class)
public class TestBackendServiceCustomer {

    @Mock
    private CustomerRepository customerRepo;
    @Mock
    private CartRepository cartRepo;

    @InjectMocks
    private CustomerService customerService;
    @InjectMocks
    private ManagerService managerService;


    private static final String CUSTOMER_KEY = "TestCustomer";
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
        
        lenient().when(cartRepo.findCartById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0) == CART_KEY) {
                Cart cart = new Cart();
                cart.setId(CART_KEY);
                cart.setCustomer(customerRepo.findCustomerByUsername(CUSTOMER_KEY));
                return cart;
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
    public void testCreateCustomer() {
        assertEquals(0, managerService.getAllCustomers().size());

        String username = "Jason";
        Customer customer = null;
        try {
            customer = customerService.createCustomer("name1", username, "pass1");
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            fail();
        }
        assertNotNull(customer);
        assertNotNull(customer.getCart());
        assertEquals(username, customer.getUsername());
    }
    
    @Test
    public void testGetExistingCustomer() {
        assertEquals(CUSTOMER_KEY, customerService.getCustomer(CUSTOMER_KEY).getUsername());
        assertEquals(CART_KEY, customerService.getCustomer(CUSTOMER_KEY).getCart().getId());
    }
    

    @Test
    public void testGetNonExistingCustomer() {
    	String error = "";
    	try {
    		customerService.getCustomer(NON_EXISTING_USER_KEY);
    	} catch (IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	
    	assertEquals("No customer with username:" + NON_EXISTING_USER_KEY + " exists!", error);
    }

    
    @Test
    public void testCreateCustomerNullUsernameAndPassword() {
         String name = null;
         String error = null;
         Customer customer = null;
         
         try {
             customer = customerService.createCustomer(name,name,name);
         } catch (IllegalArgumentException e) {
             error = e.getMessage();
         }

         assertNull(customer);
         // check error
         assertEquals("username cannot be empty! password cannot be empty! name cannot be empty!", error);

     }
    
    @Test
    public void testDeleteNonExistingCustomer() {
    	String error = null;
    	try {
    		customerService.deleteCustomer(NON_EXISTING_USER_KEY);
    	} catch (IllegalArgumentException e) {
    		error = e.getMessage();
    	}
         
        assertEquals("No customer with username:" + NON_EXISTING_USER_KEY + " exists!", error);
    }
    
    @Test
    public void testDeleteCustomerNullName() {
        String error = null;
        try {
            customerService.deleteCustomer(null);
        }
        catch(IllegalArgumentException err) {
            error = err.getMessage();
        }
        assertEquals("Customer name cannot be empty!",error);
    }
    
    @Test
    public void testDeleteExistingCustomer() {
        Customer customer = customerService.deleteCustomer(CUSTOMER_KEY);  
        assertNotNull(customer);
    }
    
    private int getId() {
        return allId++;
    }
}