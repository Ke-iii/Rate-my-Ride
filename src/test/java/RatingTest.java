import com.example.cab302project.Model.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RatingTest {

    private Rating rating;

    @BeforeEach
    void setUp() {
        // Initialize a Rating object before each test
        rating = new Rating("johndoe", 4.5f, 101);
    }

    @Test
    void testGetId() {
        assertEquals(0, rating.getId()); // Initial ID is 0
    }

    @Test
    void testSetId() {
        rating.setId(1);
        assertEquals(1, rating.getId());
    }

    @Test
    void testGetUsername() {
        assertEquals("johndoe", rating.getUsername());
    }

    @Test
    void testSetUsername() {
        rating.setUsername("janedoe");
        assertEquals("janedoe", rating.getUsername());
    }

    @Test
    void testGetRating() {
        assertEquals(4.5f, rating.getRating());
    }

    @Test
    void testSetRating() {
        rating.setRating(5.0f);
        assertEquals(5.0f, rating.getRating());
    }

    @Test
    void testGetPostId() {
        assertEquals(101, rating.getPost_id());
    }

    @Test
    void testSetPostId() {
        rating.setPost_id(202);
        assertEquals(202, rating.getPost_id());
    }
}
