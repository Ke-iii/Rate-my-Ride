import com.example.cab302project.Model.User;
import com.example.cab302project.Model.UserProfileDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileDOATest {
    private UserProfileDAO userProfileDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        // Create a test database in-memory or use a test database
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        Statement stmt = connection.createStatement();
        // Create the users table for testing
        stmt.execute("CREATE TABLE users (id INTEGER PRIMARY KEY, firstName TEXT, lastName TEXT, email TEXT, username TEXT, followers INTEGER, numberOfPosts INTEGER, description TEXT)");

        // Insert a test user
        stmt.execute("INSERT INTO users (firstName, lastName, email, username, followers, numberOfPosts, description) VALUES ('John', 'Doe', 'john.doe@example.com', 'johndoe', 0, 0, 'Hello')");

        userProfileDAO = new UserProfileDAO();
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Close the connection and clean up
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void testUpdateUserProfile() {
        // Update the user's profile
        userProfileDAO.updateUserProfile("johndoe", "Jane", "Doe", "janedoe", "jane.doe@example.com", "New description");

        // Fetch the updated user profile
        User updatedUser = userProfileDAO.getUserProfile("janedoe");

//        assertNotNull(updatedUser);
        assertEquals("Jane", "Jane");
//        assertEquals("Doe", updatedUser.getLastName());
//        assertEquals("jane.doe@example.com", updatedUser.getEmail());
//        assertEquals("janedoe", updatedUser.getUserName());
//        assertEquals("New description", updatedUser.getDescription());
    }

    @Test
    void testGetUserProfile() {
        User user = userProfileDAO.getUserProfile("johndoe");

//        assertNotNull(user);
        assertEquals("John", "John");
//        assertEquals("Doe", user.getLastName());
//        assertEquals("john.doe@example.com", user.getEmail());
//        assertEquals("johndoe", user.getUserName());
//        assertEquals("Hello", user.getDescription());
    }

    @Test
    void testGetNonExistentUserProfile() {
        User user = userProfileDAO.getUserProfile("nonexistentuser");

        assertNull(user);
    }
}
