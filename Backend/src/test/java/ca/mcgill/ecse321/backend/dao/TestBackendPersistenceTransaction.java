package ca.mcgill.ecse321.backend.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Cart;
import ca.mcgill.ecse321.backend.model.Customer;
import ca.mcgill.ecse321.backend.model.Transaction;

@ExtendWith(MockitoExtension.class)
public class TestBackendPersistenceTransaction {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ArtistRepository artistRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private TransactionRepository transactionRepository;

    private static final String CUSTOMER_KEY = "TestCustomer";
    private static final String ARTIST_KEY = "TestArtist";
    private static final Integer TRANSACTION_KEY = 1;
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

        lenient().when(artistRepository.findArtistByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ARTIST_KEY)) {
                Artist artist = new Artist();
                artist.setUsername(ARTIST_KEY);
                artist.setId(getId());
                return artist;
            } else {
                return null;
            }
        });

        lenient().when(transactionRepository.findTransactionById(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0) == TRANSACTION_KEY) {
                        Transaction transaction = new Transaction();
                        transaction.setId(getId());
                        transaction.setArtist(artistRepository.findArtistByUsername(ARTIST_KEY));
                        transaction.setCustomer(customerRepository.findCustomerByUsername(CUSTOMER_KEY));
                        transaction.setPrice(69.0);
                        return transaction;
                    } else {
                        return null;
                    }
                });
        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(artistRepository.save(any(Artist.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(cartRepository.save(any(Cart.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(transactionRepository.save(any(Transaction.class))).thenAnswer(returnParameterAsAnswer);

    }

    @AfterEach
    public void clearDatabase() {
        // Fisrt, we clear registrations to avoid exceptions due to inconsistencies
        transactionRepository.deleteAll();
        cartRepository.deleteAll();
        artistRepository.deleteAll();
        customerRepository.deleteAll();
        // Then we can clear the other tables
    }

  
    @Test
    public void testPersistTransaction() {
        int id = 97;
        int commission = 900;
        Customer customer = getTestCustomer();
        Artist artist = getTestArtist();

        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setCommissionApplied(commission);

        artist = artistRepository.save(artist);
        customer = customerRepository.save(customer);

        transaction.setCustomer(customer);
        transaction.setArtist(artist);
        artist.getTransaction().add(transaction);
        customer.getTransaction().add(transaction);
        transaction = transactionRepository.save(transaction);
        artist = artistRepository.save(artist);
        customer = customerRepository.save(customer);

        assertEquals(id, transaction.getId());
        assertEquals(commission, transaction.getCommissionApplied());
        assertEquals(customer.getUsername(), transaction.getCustomer().getUsername());
        assertTrue(artist.getTransaction().contains(transaction));
        assertTrue(customer.getTransaction().contains(transaction));

    }

    @Test
    public void testDeleteTransaction() {
        int id = 97;
        int commission = 900;
        Customer customer = getTestCustomer();
        Artist artist = getTestArtist();

        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setCommissionApplied(commission);

        artist = artistRepository.save(artist);
        customer = customerRepository.save(customer);

        transaction.setCustomer(customer);
        transaction.setArtist(artist);
        artist.getTransaction().add(transaction);
        customer.getTransaction().add(transaction);
        transaction = transactionRepository.save(transaction);
        artist = artistRepository.save(artist);
        customer = customerRepository.save(customer);

        assertEquals(id, transaction.getId());
        assertEquals(commission, transaction.getCommissionApplied());
        assertEquals(customer.getUsername(), transaction.getCustomer().getUsername());
        assertTrue(artist.getTransaction().contains(transaction));
        assertTrue(customer.getTransaction().contains(transaction));

        // assert the transaction is gone from database and the artist and customer list
        transactionRepository.delete(transactionRepository.findTransactionById(id));
        assertNotEquals(transaction, transactionRepository.findTransactionById(id));

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
    private Artist getTestArtist() {
        String name = "TestArtist";
        String username = "user1";
        String password = "123456";
        boolean ship = true;

        Artist artist = new Artist();
        artist.setName(name);
        artist.setUsername(username);
        artist.setPassword(password);
        artist.setShipToGallery(ship);
        return artist;

    }

    private int getId() {
        return allId++;
    }
   }
