import com.example.cab302project.Model.Post;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class PostTest {
    @Test
    public void testPostConstructor() {
        byte[] image = new byte[] {1, 2, 3};
        Post post = new Post("Title", "Description", "Author", "Model", "Make", "Location", image, 0, 0, 0);

        assertEquals("Title", post.getTitle());
        assertEquals("Description", post.getDescription());
        assertEquals("Author", post.getAuthor());
        assertEquals("Model", post.getModel());
        assertEquals("Make", post.getMake());
        assertEquals("Location", post.getLocation());
        assertArrayEquals(image, post.getPostImage());
        assertEquals(0, post.getRating());
        assertEquals(0, post.getNumComments());
        assertEquals(0, post.getNumshares());
    }

    @Test
    public void testSettersAndGetters() {
        Post post = new Post("Title", "Description", "Author", "Model", "Make", "Location", new byte[0], 0, 0, 0);

        post.setId(1);
        post.setTitle("New Title");
        post.setDescription("New Description");
        post.setAuthor("New Author");
        post.setModel("New Model");
        post.setMake("New Make");
        post.setLocation("New Location");
        post.setPostImage(new byte[] {4, 5, 6});
        post.setRating(10);
        post.setNumComments(20);
        post.setNumshares(30);

        assertEquals(1, post.getId());
        assertEquals("New Title", post.getTitle());
        assertEquals("New Description", post.getDescription());
        assertEquals("New Author", post.getAuthor());
        assertEquals("New Model", post.getModel());
        assertEquals("New Make", post.getMake());
        assertEquals("New Location", post.getLocation());
        assertArrayEquals(new byte[] {4, 5, 6}, post.getPostImage());
        assertEquals(10, post.getRating());
        assertEquals(20, post.getNumComments());
        assertEquals(30, post.getNumshares());
    }

    @Test
    public void testDefaultValues() {
        Post post = new Post("Title", "Description", "Author", "Model", "Make", "Location", new byte[0], 0, 0, 0);

        assertEquals(0, post.getRating());
        assertEquals(0, post.getNumComments());
        assertEquals(0, post.getNumshares());
    }

}
