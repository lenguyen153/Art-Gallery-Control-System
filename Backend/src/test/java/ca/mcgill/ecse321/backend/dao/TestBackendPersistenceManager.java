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
import ca.mcgill.ecse321.backend.model.Manager;


@ExtendWith(MockitoExtension.class)
public class TestBackendPersistenceManager {

    @Mock
    private ManagerRepository managerRepository;

    private static final String MANAGER_KEY = "TestManager";
    private static int allId = 10;

    @BeforeEach
    public void setMockOutput() {

        lenient().when(managerRepository.findManagerByUsername(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(MANAGER_KEY)) {
                        Manager manager = new Manager();
                        manager.setUsername(MANAGER_KEY);
                        manager.setId(getId());
                        return manager;
                    } else {
                        return null;
                    }
                });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(managerRepository.save(any(Manager.class))).thenAnswer(returnParameterAsAnswer);

    }

    @AfterEach
    public void clearDatabase() {
        // Fisrt, we clear registrations to avoid exceptions due to inconsistencies
        managerRepository.deleteAll();
        // Then we can clear the other tables
    }

    @Test
    public void testPersistManager() {
        // create the field and the entity
        Manager manager = getTestManager();
        String username = manager.getUsername();
        String name = manager.getName();
        manager = managerRepository.save(manager);

        // asserts that the fields are still the same
        assertNotNull(manager);
        assertEquals(username, manager.getUsername());
        assertEquals(name, manager.getName());
    }

    @Test
    public void testLoadManager() {
        assertEquals(MANAGER_KEY, managerRepository.findManagerByUsername(MANAGER_KEY).getUsername());
    }

    @Test
    public void testDeleteManager() {
        // create the field and the entity
        Manager manager = getTestManager();
        String username = manager.getUsername();
        String name = manager.getName();
        manager = managerRepository.save(manager);

        // asserts that the fields are still the same
        assertNotNull(manager);
        assertEquals(username, manager.getUsername());
        assertEquals(name, manager.getName());

        // once we know the manager is indeed in the database, delete and check if it's
        // truly gone
        managerRepository.delete(managerRepository.findManagerByUsername(username));
        assertNotEquals(manager, (managerRepository.findManagerByUsername(username)));
    }

    private Manager getTestManager() {
        String name = "TestManager";
        String username = "user1";
        String password = "123456";

        Manager manager = new Manager();
        manager.setName(name);
        manager.setUsername(username);
        manager.setPassword(password);
        return manager;

    }

    private int getId() {
        return allId++;
    }
}
