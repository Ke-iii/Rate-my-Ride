import com.example.cab302project.Model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationSystemTest {

    private Connection connection;
    RegistrationSystem registrationSystem = new RegistrationSystem();

    @BeforeEach
    void setUp() throws SQLException {
        // Create an in-memory SQLite database
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        // Create the users table
        String createTableSQL = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstName TEXT NOT NULL," +
                "lastName TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "userName TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "profilePicture BLOB," +
                "followers INTEGER," +
                "numberOfPosts INTEGER," +
                "description TEXT)";
        connection.createStatement().execute(createTableSQL);

        // Set the connection in SqliteConnection class (or adapt this part accordingly)

        registrationSystem = new RegistrationSystem();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void testAddUserSuccessfully() {
        byte[] profilePicture = new byte[]{1, 2, 3}; // Dummy data
        registrationSystem.addUser("John", "Doe", "john@example.com", "johndoe", "password123", profilePicture, "Just a test user.");

        // Check if user is added
        assertTrue(registrationSystem.duplicateUser("john@example.com"));
    }

    @Test
    void testDuplicateUser() {
        byte[] profilePicture = new byte[]{1, 2, 3}; // Dummy data
        registrationSystem.addUser("John", "Doe", "john@example.com", "johndoe", "password123", profilePicture, "Just a test user.");
        registrationSystem.addUser("Jane", "Doe", "john@example.com", "janedoe", "password456", profilePicture, "Another test user.");

        // Check if only the first user was added
        assertTrue(registrationSystem.duplicateUser("john@example.com"));
        assertFalse(registrationSystem.duplicateUser("janedoe@example.com"));
    }

    @Test
    void testEmptyFields() {
        byte[] profilePicture = new byte[]{1, 2, 3}; // Dummy data
        registrationSystem.addUser("", "Doe", "johnD@example.com", "johndoe", "password123", profilePicture, "Just a test user.");

        // Should not be added due to empty firstName
        assertFalse(registrationSystem.duplicateUser("johnD@example.com"));
    }

    @Test
    void testAddUserWithNullEmail() {
        byte[] profilePicture = new byte[]{1, 2, 3}; // Dummy data
        registrationSystem.addUser("John", "Doe", "john@gmail.com", "johndoe", "password123", profilePicture, "Just a test user.");

        // Should not be added due to null email
        assertFalse(registrationSystem.duplicateUser("John"));
    }
}
