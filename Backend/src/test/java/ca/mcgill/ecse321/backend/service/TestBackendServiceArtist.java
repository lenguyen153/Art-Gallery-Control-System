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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ca.mcgill.ecse321.backend.dao.ArtistRepository;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Artwork;


@ExtendWith(MockitoExtension.class)
public class TestBackendServiceArtist {

    @Mock
    private ArtistRepository artistRepo;


    @InjectMocks
    private ArtistService artistService;
    @InjectMocks
    private ManagerService managerService;
    

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
                artist.addArtwork(artwork);
                return artist;
            } else {
                return null;
            }
        });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(artistRepo.save(any(Artist.class))).thenAnswer(returnParameterAsAnswer);

    }
    
    @Test
    public void testCreateArtist() {
        assertEquals(0, managerService.getAllArtists().size());

        String username = "Oscar";
        Artist artist = null;
        try {
            artist = artistService.createArtist("name1", username, "pass1");
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            fail();
        }
        assertNotNull(artist);
        assertEquals(username, artist.getUsername());
    }  
    
    @Test
    public void testGetExistingArtist() {
        assertEquals(ARTIST_KEY, artistService.getArtist(ARTIST_KEY).getUsername());
        assertEquals(ARTWORK_KEY, artistService.getArtist(ARTIST_KEY).getArtwork().get(0).getName());
    }
    
    @Test
    public void testGetNonExistingArtist() {
        assertNull(artistService.getArtist(NON_EXISTING_USER_KEY));
    }
    
    @Test
    public void testUpdateCommisionArtist() {
        Artist artist = artistService.updateCommisionArtist(ARTIST_KEY, 10.0);
        assertNotNull(artist);
        assertEquals(10.0,artist.getSpecificCommisionApplied());
    }
    
    @Test
    public void testUpdateCommisionNonExistingArtist() {
        assertNull(artistService.updateCommisionArtist(NON_EXISTING_USER_KEY, 10.0));
    }
    
    @Test
    public void testUpdateShipmentExistingArtist() {
        Artist artist = artistService.updateShipmentArtist(ARTIST_KEY, true);
        assertNotNull(artist);
        assertEquals(true,artist.isShipToGallery());
        artist = artistService.updateShipmentArtist(ARTIST_KEY, false);
        assertNotNull(artist);
        assertEquals(false,artist.isShipToGallery());
    }
    
    @Test
    public void testUpdateShipmentNonExistingArtist() {
        assertNull(artistService.updateShipmentArtist(NON_EXISTING_USER_KEY, true));
    }
    
    @Test
    public void testCreateArtistNullUsernameAndPassword() {
         String name = null;
         String error = null;
         Artist artist = null;
         try {
             artist = artistService.createArtist(name,name,name);
         } catch (IllegalArgumentException e) {
             error = e.getMessage();
         }

         assertNull(artist);
         // check error
         assertEquals("username cannot be empty password cannot be empty name cannot be empty", error);

     }
    
    @Test
    public void testDeleteNonExistingArtist() {      
        Artist artist = artistService.deleteArtist(NON_EXISTING_USER_KEY);  
        assertNull(artist);
    }
    
    @Test
    public void testDeleteArtistNullName() {
        String error = null;
        try {
            artistService.deleteArtist(null);
        }
        catch(IllegalArgumentException err) {
            error = err.getMessage();
        }
        assertEquals("Artist name cannot be empty!",error);
    }
    
    @Test
    public void testDeleteExistingArtist() {      
        Artist artist = artistService.deleteArtist(ARTIST_KEY);  
        assertNotNull(artist);
    }
     
    
    private int getId() {
        return allId++;
    }
    
}