import com.example.cab302project.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        // Initialize a User object before each test
        user = new User(1, "John", "Doe", "john.doe@example.com", "johndoe", 100, 50, "Avid traveler and blogger.");
    }

    @Test
    void testGetId() {
        assertEquals(1, user.getId());
    }

    @Test
    void testSetId() {
        user.setId(2);
        assertEquals(2, user.getId());
    }

    @Test
    void testGetFirstName() {
        assertEquals("John", user.getFirstName());
    }

    @Test
    void testSetFirstName() {
        user.setFirstName("Jane");
        assertEquals("Jane", user.getFirstName());
    }

    @Test
    void testGetLastName() {
        assertEquals("Doe", user.getLastName());
    }

    @Test
    void testSetLastName() {
        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());
    }

    @Test
    void testGetEmail() {
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void testSetEmail() {
        user.setEmail("jane.smith@example.com");
        assertEquals("jane.smith@example.com", user.getEmail());
    }

    @Test
    void testGetUserName() {
        assertEquals("johndoe", user.getUserName());
    }

    @Test
    void testSetUserName() {
        user.setUserName("janesmith");
        assertEquals("janesmith", user.getUserName());
    }

    @Test
    void testGetFollowers() {
        assertEquals(100, user.getFollowers());
    }

    @Test
    void testSetFollowers() {
        user.setFollowers(200);
        assertEquals(200, user.getFollowers());
    }

    @Test
    void testGetNumberOfPosts() {
        assertEquals(50, user.getNumberOfPosts());
    }

    @Test
    void testSetNumberOfPosts() {
        user.setNumberOfPosts(75);
        assertEquals(75, user.getNumberOfPosts());
    }

    @Test
    void testGetDescription() {
        assertEquals("Avid traveler and blogger.", user.getDescription());
    }

    @Test
    void testSetDescription() {
        user.setDescription("Tech enthusiast and developer.");
        assertEquals("Tech enthusiast and developer.", user.getDescription());
    }
}
