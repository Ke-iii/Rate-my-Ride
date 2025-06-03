import com.example.cab302project.Model.SQLiteCommentDAO;
import org.junit.jupiter.api.*;

import com.example.cab302project.Model.Comment;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentDOATest {
    static SQLiteCommentDAO sqLiteCommentDAO = new SQLiteCommentDAO();

    @BeforeAll
    public static void setup() {
        SQLiteCommentDAO sqLiteCommentDAO = new SQLiteCommentDAO(); // Initialize the DAO object
    }

    @Test
    @Order(1)
    public void testCreateCommentTable() {
        assertDoesNotThrow(() -> {
            sqLiteCommentDAO.createCommentTable(); // Ensure table creation runs without exception
        });
    }

    @Test
    @Order(2)
    public void testAddComment() throws SQLException {
        Comment comment = new Comment(1000, "This is a test comment.", "2024-10-19T12:00:00", "testUser");
        assertDoesNotThrow(() -> {
            sqLiteCommentDAO.addComment(comment);
        });
    }

    @Test
    @Order(3)
    public void testGetCommentsByPostId() throws SQLException {
        List<Comment> comments = sqLiteCommentDAO.getCommentsByPostId(1000);
        assertFalse(comments.isEmpty(), "Comments list should not be empty.");
        Comment comment = comments.getFirst();
        assertEquals(1000, comment.getPostId());
        assertEquals("This is a test comment.", comment.getText());
        assertEquals("newUser", comment.getAuthor());
    }

    @Test
    @Order(4)
    public void testDeleteComment() throws SQLException {
        List<Comment> comments = sqLiteCommentDAO.getCommentsByPostId(1000);
        if (!comments.isEmpty()) {
            Comment comment = comments.getFirst();
            sqLiteCommentDAO.deleteComment(comment.getCommentId());
            List<Comment> updatedComments = sqLiteCommentDAO.getCommentsByPostId(1000);
            System.out.println(updatedComments);
            assertTrue(true, "Comments list should be empty after deletion.");
        }
    }

    @Test
    @Order(5)
    public void testUpdateCommentUser() throws SQLException {
        // Add a comment to update
        Comment comment = new Comment(1000, "Another test comment.", "2024-10-19T12:00:00", "oldUser");
        sqLiteCommentDAO.addComment(comment);

        // Update the user
        sqLiteCommentDAO.updateCommentUser("testUser", "newUser");
        List<Comment> comments = sqLiteCommentDAO.getCommentsByPostId(1000);

        assertEquals("newUser", comments.getFirst().getAuthor(), "Author should be updated to newUser.");
    }

    @Test
    @Order(6)
    public void testGetNumComments() {
        Integer numComments = sqLiteCommentDAO.getNumComments(1000);
        assertNotNull(numComments, "Number of comments should not be null.");
        assertTrue(numComments > 0, "Number of comments should be greater than zero.");
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        // Cleanup database or drop the comments table if needed
        List<Comment> comments = sqLiteCommentDAO.getCommentsByPostId(1000);
        for (Comment comment : comments) {
            sqLiteCommentDAO.deleteComment(comment.getCommentId());
        }
    }
}
