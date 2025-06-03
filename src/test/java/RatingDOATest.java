import com.example.cab302project.Model.SQLiteRatingDOA;
import com.example.cab302project.Model.Rating;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatingDOATest {
    SQLiteRatingDOA ratingDAO = new SQLiteRatingDOA();

    @BeforeAll
    public static void setup() {
        SQLiteRatingDOA ratingDAO = new SQLiteRatingDOA(); // Initialize the DAO object
    }

    @Test
    @Order(1)
    public void testCreateRatingTable() {
        assertDoesNotThrow(() -> {
            ratingDAO.createCommentTable(); // Ensure table creation runs without exception
        });
    }

    @Test
    @Order(2)
    public void testAddRating() throws SQLException {
        Rating rating = new Rating("testUser", 4.5f, 1);
        assertDoesNotThrow(() -> {
            ratingDAO.addRating(rating); // Test adding a rating
        });
    }

    @Test
    @Order(3)
    public void testGetPostRating() throws SQLException {
        Float rating = ratingDAO.getPostRating(1);
        assertNotNull(rating, "Rating should not be null.");
        assertTrue(rating > 0, "Rating should be greater than 0.");
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        // Perform any cleanup if necessary
    }
}
