import com.example.cab302project.Model.ViewingUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewingUserTest {

    @Test
    void testSetAndGetSelectedUser() {
        ViewingUser.setSelectedUser("testUser");

        // Check if the user is correctly set
        assertEquals("testUser", ViewingUser.getSelectedUser());
    }

    @Test
    void testClearSelectedUser() {
        ViewingUser.setSelectedUser("testUser");

        // Clear the selected user
        ViewingUser.clearSelectedUser();

        // Check if the user is cleared
        assertNull(ViewingUser.getSelectedUser());
    }

    @Test
    void testGetSelectedUser_NoUserSet() {
        // Ensure no user is selected initially
        assertNull(ViewingUser.getSelectedUser());
    }
}
