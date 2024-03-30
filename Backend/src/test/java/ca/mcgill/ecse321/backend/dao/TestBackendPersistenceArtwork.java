package ca.mcgill.ecse321.backend.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import ca.mcgill.ecse321.backend.model.ArtStyle;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Artwork;


@ExtendWith(MockitoExtension.class)
public class TestBackendPersistenceArtwork {

    @Mock
    private ArtworkRepository artworkRepository;
    @Mock
    private ArtistRepository artistRepository;

    private static final String ARTIST_KEY = "TestArtist";
    private static final String ARTWORK_KEY = "TestArtwork";
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

        lenient().when(artworkRepository.findArtworkByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
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
        lenient().when(artistRepository.save(any(Artist.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(artworkRepository.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);

    }

    @AfterEach
    public void clearDatabase() {
        // Fisrt, we clear registrations to avoid exceptions due to inconsistencies
        artworkRepository.deleteAll();
        artistRepository.deleteAll();
        // Then we can clear the other tables
    }

    @Test
    public void testPersistAndLoadArtwork() {
        Artist artist = getTestArtist();
        // create the field and the entity
        String name = "TestArtwork";
        String description = "beauty";
        int price = 45;
        ArtStyle artStyle = ArtStyle.ART_NOUVEAU;

        Artwork artwork = new Artwork();
        artwork.setName(name);
        artwork.setDescription(description);
        artwork.setPrice(price);
        artwork.setArtStyle(artStyle);

        artist.addArtwork(artwork);
        // save then set the variable to null and recover it again
        artist = artistRepository.save(artist);
        // because the artwork is saved with the artist
        artwork = artist.getArtwork().get(0);

        // asserts that the fields are still the same
        assertNotNull(artwork);
        assertTrue(artist.getArtwork().contains(artwork));
        assertEquals(description, artwork.getDescription());
        assertEquals(name, artwork.getName());
        assertEquals(artist.getUsername(), artwork.getArtist().getUsername());
        assertEquals(price, artwork.getPrice());
        assertEquals(artStyle, artwork.getArtStyle());
    }

    @Test
    public void testDeleteArtwork() {
        Artist artist = getTestArtist();
        // create the field and the entity
        String name = "La Muse";
        String description = "beauty";
        int price = 45;
        ArtStyle artStyle = ArtStyle.ART_NOUVEAU;

        Artwork artwork = new Artwork();
        artwork.setName(name);
        artwork.setDescription(description);
        artwork.setPrice(price);
        artwork.setArtStyle(artStyle);

        artist.addArtwork(artwork);
        // save then set the variable to null and recover it again
        artist = artistRepository.save(artist);
        // because the artwork is saved with the artist
        artwork = artist.getArtwork().get(0);

        // asserts that the fields are still the same
        assertNotNull(artwork);
        assertTrue(artist.getArtwork().contains(artwork));
        assertEquals(description, artwork.getDescription());
        assertEquals(name, artwork.getName());
        assertEquals(artist.getUsername(), artwork.getArtist().getUsername());
        assertEquals(price, artwork.getPrice());
        assertEquals(artStyle, artwork.getArtStyle());

        // remove the artwork from the artist (@orphan removal will delete the object)
        artist.getArtwork().remove(artwork);
        artist = artistRepository.save(artist);
        // assert that the artwork is not in the database anymore
        assertFalse(artist.getArtwork().contains(artwork));
        assertNotEquals(name, artworkRepository.findArtworkByName(artwork.getName()));
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
