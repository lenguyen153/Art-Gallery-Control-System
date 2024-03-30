
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
import ca.mcgill.ecse321.backend.model.Artist;


@ExtendWith(MockitoExtension.class)
public class TestBackendPersistenceArtist {

    @Mock
    private ArtistRepository artistRepository;



    private static final String ARTIST_KEY = "TestArtist";
    private static int allId = 10;

    @BeforeEach
    public void setMockOutput() {


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

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(artistRepository.save(any(Artist.class))).thenAnswer(returnParameterAsAnswer);
        
    }

    @AfterEach
    public void clearDatabase() {
        // Fisrt, we clear registrations to avoid exceptions due to inconsistencies
        artistRepository.deleteAll();
        // Then we can clear the other tables
    }

    @Test
    public void testPersistArtist() {
        // create the field and the entity
        Artist artist = getTestArtist();
        String username = artist.getUsername();
        String name = artist.getName();
        artist = artistRepository.save(artist);

        // asserts that the fields are still the same
        assertNotNull(artist);
        assertEquals(username, artist.getUsername());
        assertEquals(name, artist.getName());
    }

    @Test
    public void testLoadArtist() {
        assertEquals(ARTIST_KEY, artistRepository.findArtistByUsername(ARTIST_KEY).getUsername());
    }

    @Test
    public void testDeleteArtist() {
        Artist artist = getTestArtist();
        String username = artist.getUsername();
        String name = artist.getName();
        artist = artistRepository.save(artist);

        // asserts that the fields are still the same
        assertNotNull(artist);
        assertEquals(username, artist.getUsername());
        assertEquals(name, artist.getName());

        // once we know the artist is indeed in the database, delete and check if it's
        // truly gone
        artistRepository.delete(artistRepository.findArtistByUsername(username));
        assertNotEquals(artist, (artistRepository.findArtistByUsername(username)));
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