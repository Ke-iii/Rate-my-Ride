import com.example.cab302project.Model.SQLiteFollowDOA;
import com.example.cab302project.Model.SQLitePostDOA;
import com.example.cab302project.Model.SqliteConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FollowDOATest {
    private SQLiteFollowDOA followDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        followDAO = new SQLiteFollowDOA();
        connection = SqliteConnection.connect();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Close the connection after each test
        connection.close();
    }

    @Test
    @Order(1)
    public void testInsertFollower() throws SQLException {
        followDAO.insertFollower("user1", "user2");

        // Check if the follower was inserted
        assertTrue(followDAO.isFollower("user1", "user2"));
    }

    @Test
    @Order(2)
    public void testIsFollower() throws SQLException {
        followDAO.insertFollower("user1", "user2");

        assertTrue(followDAO.isFollower("user1", "user2"));
        assertFalse(followDAO.isFollower("user1", "user1")); // Not a follower
    }

    @Test
    @Order(3)
    public void testUnfollow() throws SQLException {
        followDAO.insertFollower("user1", "user2");
        assertTrue(followDAO.isFollower("user1", "user2"));

        followDAO.unfollow("user1", "user2");
        assertFalse(followDAO.isFollower("user1", "user2")); // Should no longer be a follower
    }

    @Test
    @Order(4)
    public void testMultipleFollowers() throws SQLException {
        followDAO.insertFollower("user1", "user2");
        followDAO.insertFollower("user1", "user3");

        assertTrue(followDAO.isFollower("user1", "user2"));
        assertTrue(followDAO.isFollower("user1", "user3"));
    }
}
