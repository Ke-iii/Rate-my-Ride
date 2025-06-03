import com.example.cab302project.Model.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    private Comment comment;

    @BeforeEach
    void setUp() {
        // Initialize a Comment object before each test
        comment = new Comment(101, "This is a test comment.", "2024-10-21 10:00:00", "johndoe");
    }

    @Test
    void testGetCommentId() {
        assertEquals(0, comment.getCommentId()); // Initial ID should be 0
    }

    @Test
    void testSetCommentId() {
        comment.setCommentId(1);
        assertEquals(1, comment.getCommentId());
    }

    @Test
    void testGetPostId() {
        assertEquals(101, comment.getPostId());
    }

    @Test
    void testSetPostId() {
        comment.setPostId(202);
        assertEquals(202, comment.getPostId());
    }

    @Test
    void testGetText() {
        assertEquals("This is a test comment.", comment.getText());
    }

    @Test
    void testSetText() {
        comment.setText("Updated comment text.");
        assertEquals("Updated comment text.", comment.getText());
    }

    @Test
    void testGetTimestamp() {
        assertEquals("2024-10-21 10:00:00", comment.getTimestamp());
    }

    @Test
    void testSetTimestamp() {
        comment.setTimestamp("2024-10-22 12:00:00");
        assertEquals("2024-10-22 12:00:00", comment.getTimestamp());
    }

    @Test
    void testGetAuthor() {
        assertEquals("johndoe", comment.getAuthor());
    }

    @Test
    void testSetAuthor() {
        comment.setAuthor("janedoe");
        assertEquals("janedoe", comment.getAuthor());
    }
}
