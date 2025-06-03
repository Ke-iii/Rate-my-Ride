import com.example.cab302project.Controller.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    @BeforeEach
    void setUp() {
        // Clear the session before each test to ensure tests are independent
        LoginController.Session.clearSession();
    }

    @Test
    void testSetLoggedInUser() {
        LoginController.Session.setLoggedInUser("testUser");
        assertEquals("testUser", LoginController.Session.getLoggedInUser(), "The logged in user should be 'testUser'");
    }

    @Test
    void testGetLoggedInUser() {
        LoginController.Session.setLoggedInUser("johnDoe");
        assertEquals("johnDoe", LoginController.Session.getLoggedInUser(), "The logged in user should be 'johnDoe'");
    }

    @Test
    void testClearSession() {
        LoginController.Session.setLoggedInUser("janeDoe");
        LoginController.Session.clearSession();
        assertNull(LoginController.Session.getLoggedInUser(), "The session should be cleared, and no user should be logged in.");
    }

    @Test
    void testGetLoggedInUserWithoutSetting() {
        assertNull(LoginController.Session.getLoggedInUser(), "No user should be logged in if not set.");
    }
}
