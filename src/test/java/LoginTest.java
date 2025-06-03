import com.example.cab302project.Model.LoginSystem;
import com.example.cab302project.Model.SqliteConnection;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private LoginSystem loginSystem;

    @BeforeEach
    public void setUp() {
        SqliteConnection.setupDatabase();  // Set up the database connection
        loginSystem = new LoginSystem();   // Create a new instance of LoginSystem
        assertNotNull(loginSystem, "LoginSystem should be initialized");
    }

    @Test
    public void testValidLogin() {
        boolean result = loginSystem.checkLogin("admin", "1234");
        assertTrue(result, "Login should be successful with valid credentials");
    }

    @Test
    public void testInvalidLogin_WrongPassword() {
        boolean result = loginSystem.checkLogin("admin", "wrongpassword");
        assertFalse(result, "Login should fail with incorrect password");
    }

    @Test
    public void testInvalidLogin_WrongUsername() {
        boolean result = loginSystem.checkLogin("nonexistent", "1234");
        assertFalse(result, "Login should fail with non-existent username");
    }
}
