package ca.mcgill.ecse321.backend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ca.mcgill.ecse321.backend.dao.ArtistRepository;
import ca.mcgill.ecse321.backend.dao.ArtworkRepository;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Artwork;


@ExtendWith(MockitoExtension.class)
public class TestBackendServiceArtwork {


    @Mock
    private ArtistRepository artistRepo;
    @Mock
    private ArtworkRepository artworkRepo;

    @InjectMocks
    private ManagerService managerService;
    @InjectMocks
    private ArtworkService artworkService;
    

    private static final String ARTIST_KEY = "TestArtist";
    private static final String ARTWORK_KEY = "TestArtwork";
    private static final String NON_EXISTING_USER_KEY = "DoNotExist";
    private static int allId = 10;

    @BeforeEach
    public void setMockOutput() {

        lenient().when(artistRepo.findArtistByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ARTIST_KEY)) {
                Artist artist = new Artist();
                artist.setUsername(ARTIST_KEY);
                artist.setProfile("I am an artist");
                artist.setId(getId());
                Artwork artwork = new Artwork();
                artwork.setName(ARTWORK_KEY);
                artwork.setArtist(artist);
                artist.addArtwork(artwork);
                return artist;
            } else {
                return null;
            }
        });

        lenient().when(artworkRepo.findArtworkByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ARTWORK_KEY)) {
                Artwork artwork = new Artwork();
                Artist artist = new Artist();
                artist.setUsername(ARTIST_KEY);
                artwork.setArtist(artist);
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
        lenient().when(artistRepo.save(any(Artist.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(artworkRepo.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);

    }

    @Test
    public void testCreateArtwork() {
        assertEquals(0, managerService.getAllArtworks().size());
        String name = "Masterpiece";
        Artwork artwork = null;
        try {
            artwork = artworkService.createArtwork(ARTIST_KEY, name, "a beautiful piece", "ABSTRACT", 1, 10);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            fail();
        }
        assertNotNull(artwork);
        assertEquals(name, artwork.getName());
    }
    
    @Test
    public void testUpdateArtwork() {
        Artwork artwork = artworkService.updateArtwork(ARTWORK_KEY, "bla", "ABSTRACT", 10, 7);
        assertNotNull(artwork);
        assertEquals("bla",artwork.getDescription());
    }
    
    @Test
    public void testUpdateNonExistingArtwork() {
        assertNull(artworkService.updateArtwork(NON_EXISTING_USER_KEY, "bla", "ABSTRACT", 10, 5));
    }
    
    @Test
    public void testUpdateExistingArtworkNullName() {
        String error = null;
        try {
            artworkService.updateArtwork(null, "bla", "ABSTRACT", 10, 5);
        }
        catch(IllegalArgumentException err) {
            error = err.getMessage();
        }
        assertEquals("Artwork name cannot be empty!",error);
    }

    @Test
    public void testGetExistingArtwork() {
        assertEquals(ARTWORK_KEY, artworkService.getArtwork(ARTWORK_KEY).getName());
    }

    @Test
    public void testGetNonExistingArtwork() {
        // random name (so we can keep non existing user key)
        assertNull(artworkService.getArtwork(NON_EXISTING_USER_KEY));
    }

    @Test
    public void testCreateArtworkWrongValue() {
        String name = null;
        String error = null;
        Artwork artwork = null;

        try {
            artwork = artworkService.createArtwork(name, name, name, name, 0, -10);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(artwork);
        // check error
        assertEquals(
                "Artist needs to be selected for artwork! Artwork name cannot be empty! "
                        + "Artwork description cannot be empty! " + "Artwork artstyle cannot be empty! "
                        + "Artwork number of copies cannot be less than 1! " + "Artwork price cannot be negative!",
                error);
    }

    @Test
    public void testFindAllArtwork() {
        List<Artwork> list = artworkService.getAllArtworks();
        assertNotNull(list);
    }
    
    @Test
    public void testDeleteNonExistingArtwork() {      
        Artwork artwork = artworkService.deleteArtwork(NON_EXISTING_USER_KEY);  
        assertNull(artwork);
    }
    
    @Test
    public void testDeleteArtworkNullName() {
        String error = null;
        try {
            artworkService.deleteArtwork(null);
        }
        catch(IllegalArgumentException err) {
            error = err.getMessage();
        }
        assertEquals("Artwork name cannot be empty!",error);
    }
    
    @Test
    public void testDeleteExistingArtwork() {
        Artist artist = artistRepo.findArtistByUsername(ARTIST_KEY);
        Artwork artwork = artist.getArtwork().get(0);
        artwork = artworkService.deleteArtwork(artwork.getName());  
        assertNotNull(artwork);
    }

    private int getId() {
        return allId++;
    }
}
