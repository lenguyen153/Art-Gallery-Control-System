
package ca.mcgill.ecse321.backend.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Customer;

@ExtendWith(MockitoExtension.class)
public class TestBackendPersistenceCustomer {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CustomerRepository customerRepository;


    private static final String CUSTOMER_KEY = "TestCustomer";
    private static int allId = 10;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(customerRepository.findCustomerByUsername(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(CUSTOMER_KEY)) {
                        Customer customer = new Customer();
                        Cart cart = new Cart();
                        customer.setUsername(CUSTOMER_KEY);
                        customer.setCart(cart);
                        customer.setId(getId());
                        cart.setCustomer(customer);
                        cart.setId(getId());
                        return customer;
                    } else {
                        return null;
                    }
                });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(cartRepository.save(any(Cart.class))).thenAnswer(returnParameterAsAnswer);

    }

    @AfterEach
    public void clearDatabase() {
        // Fisrt, we clear registrations to avoid exceptions due to inconsistencies
        cartRepository.deleteAll();
        customerRepository.deleteAll();
        // Then we can clear the other tables
    }

    @Test
    public void testPersistCustomer() {
        // create the field and the entity
        Customer customer = getTestCustomer();
        String username = customer.getUsername();
        String name = customer.getName();
        customer = customerRepository.save(customer);

        // asserts that the fields are still the same
        assertNotNull(customer);
        assertEquals(username, customer.getUsername());
        assertEquals(name, customer.getName());
    }

    @Test
    public void testLoadCustomer() {
        assertEquals(CUSTOMER_KEY, customerRepository.findCustomerByUsername(CUSTOMER_KEY).getUsername());
    }

    @Test
    public void testDeleteCustomer() {
        // create the field and the entity
        Cart cart = getTestCart();
        // create the field and the entity
        Customer customer = getTestCustomer();
        String username = customer.getUsername();
        String name = customer.getName();
        customer.setCart(cart);
        customer = customerRepository.save(customer);

        // asserts that the fields are still the same
        assertNotNull(customer);
        assertEquals(username, customer.getUsername());
        assertEquals(name, customer.getName());

        // once we know the customer is indeed in the database, delete and check if it's
        // truly gone
        customerRepository.delete(customerRepository.findCustomerByUsername(username));
        assertNotEquals(customer, (customerRepository.findCustomerByUsername(username)));

        // assert the cart is null
        customerRepository.delete(customerRepository.findCustomerByUsername(username));
        assertNotEquals(customer, customerRepository.findCustomerByUsername(username));
        assertNotEquals(cart, cartRepository.findCartById(customer.getCart().getId()));
    }

    private Customer getTestCustomer() {
        String name = "TestCustomer";
        String username = "user1";
        String password = "123456";

        Customer customer = new Customer();
        customer.setName(name);
        customer.setUsername(username);
        customer.setPassword(password);
        return customer;

    }

    private Cart getTestCart() {
        int id = 90;
        Cart cart = new Cart();
        cart.setId(id);
        return cart;
    }

    private int getId() {
        return allId++;
    }

}