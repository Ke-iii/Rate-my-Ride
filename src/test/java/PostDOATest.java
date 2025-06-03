import com.example.cab302project.Model.Post;
import com.example.cab302project.Model.SQLitePostDOA;
import com.example.cab302project.Model.SqliteConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostDOATest {
    private SQLitePostDOA postDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() {
        // Assuming SqliteConnection.connect() is the method that sets up the SQLite connection.
        postDAO = new SQLitePostDOA();
        connection = SqliteConnection.connect();
        clearPostsTable();
    }

    @AfterEach
    public void tearDown() {
        clearPostsTable();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearPostsTable() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM posts");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateTable() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM sqlite_master WHERE type='table' AND name='posts'");
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next(), "Table 'posts' should be created");
            resultSet.close();
        } catch (SQLException e) {
            fail("Exception while checking table creation: " + e.getMessage());
        }
    }

    @Test
    public void testAddPost() {
        Post post = new Post("Test Title", "Test Description", "admin",
                "Ford", "Mustang", "Location", null, 0, 0, 0);
        postDAO.addPost(post, "admin");

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts WHERE postTitle = ?");
            statement.setString(1, "Test Title");
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next(), "Post should be added to the database");
            assertEquals("Test Title", resultSet.getString("postTitle"));
            assertEquals("Mustang", resultSet.getString("carMake"));
            resultSet.close();
        } catch (SQLException e) {
            fail("Exception while fetching post: " + e.getMessage());
        }
    }
    @Test
    public void testAddEmptyPost() {
        Post post = new Post("", "", "","", "", "", null, 0, 0, 0);
        postDAO.addPost(post, "admin");

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts WHERE postTitle = ?");
            statement.setString(1, "");
            ResultSet resultSet = statement.executeQuery();
            assertFalse(resultSet.next(), "Empty post should not be added to the database");
            resultSet.close();
        } catch (SQLException e) {
            fail("Exception while fetching post: " + e.getMessage());
        }
    }

    @Test
    public void testUpdatePost() throws SQLException {
        Post post = new Post("Test Title", "Test Description", "admin", "Ford", "Mustang", "Location", null, 0, 0, 0);
        postDAO.addPost(post, "admin");

        post.setTitle("Updated Title");
        post.setDescription("Updated Description");
        postDAO.updatePost(post, post.getId());

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts WHERE postTitle = ?");
        statement.setString(1, "Updated Title");
        ResultSet resultSet = statement.executeQuery();
        assertTrue(resultSet.next(), "Post should be updated");
        assertEquals("Updated Title", resultSet.getString("postTitle"));
        assertEquals("Updated Description", resultSet.getString("postDescription"));
        resultSet.close();
    }
    @Test
    public void testEmptyUpdatePost() throws SQLException {
        Post post = new Post("Test Title", "Test Description", "admin", "Ford", "Mustang", "Location", null, 0, 0, 0);
        postDAO.addPost(post, "admin");

        post.setTitle("");
        post.setDescription("");
        postDAO.updatePost(post, post.getId());

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts WHERE postTitle = ?");
        statement.setString(1, "Test Title");
        ResultSet resultSet = statement.executeQuery();
        assertTrue(resultSet.next(), "Post should be updated");
        assertEquals("Test Title", resultSet.getString("postTitle"));
        assertEquals("Test Description", resultSet.getString("postDescription"));
        resultSet.close();
    }

    @Test
    public void testDeletePost() {
        Post post = new Post("Test Title", "Test Description", "admin","Ford", "Mustang", "Location", null, 0, 0, 0);
        postDAO.addPost(post, "admin");

        postDAO.deletePost(post.getId());

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts WHERE postTitle = ?");
            statement.setString(1, "Test Title");
            ResultSet resultSet = statement.executeQuery();
            assertFalse(resultSet.next(), "Post should be deleted from the database");
            resultSet.close();
        } catch (SQLException e) {
            fail("Exception while fetching post: " + e.getMessage());
        }
    }

    @Test
    public void testGetNumPosts() throws SQLException {
        Post post1 = new Post("Title 1", "Description 1", "admin","Ford", "Mustang", "Location", null, 0, 0, 0);
        Post post2 = new Post("Title 2", "Description 2", "admin","Ford", "Mustang", "Location", null, 0, 0, 0);

        postDAO.addPost(post1, "admin");
        postDAO.addPost(post2, "admin");

        int numPosts = postDAO.getNumPosts("admin");
        assertEquals(2, numPosts, "Number of posts should be 2");
    }

    @Test
    public void testGetPostsByAuthor() {
        Post post1 = new Post("Title 1", "Description 1", "admin","Ford", "Mustang", "Location", null, 0, 0, 0);
        Post post2 = new Post("Title 2", "Description 2", "admin","Ford", "Mustang", "Location", null, 0, 0, 0);

        postDAO.addPost(post1, "admin");
        postDAO.addPost(post2, "admin");

        List<Post> posts = SQLitePostDOA.getPostsByAuthor("admin");
        assertEquals(2, posts.size(), "There should be 2 posts for the user 'testUser'");
        assertEquals("Title 1", posts.get(0).getTitle(), "The title of the first post should be 'Title 1'");
        assertEquals("Title 2", posts.get(1).getTitle(), "The title of the second post should be 'Title 2'");
    }
}
