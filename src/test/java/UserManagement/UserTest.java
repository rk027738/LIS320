package UserManagement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        // Initialize a User object before each test
        user = new User(1, "john_doe", "securepassword", "user");
    }

    @Test
    public void testConstructor() {
        // Verify constructor initialization
        assertEquals(1, user.getId(), "User ID should be initialized correctly.");
        assertEquals("john_doe", user.getUsername(), "Username should be initialized correctly.");
        assertEquals("securepassword", user.getPassword(), "Password should be initialized correctly.");
        assertEquals("user", user.getRole(), "Role should be initialized correctly.");
    }

    @Test
    public void testGettersAndSetters() {
        // Update fields using setters
        user.setUserId(2);
        user.setUsername("jane_doe");
        user.setPassword("newpassword");
        user.setRole("admin");

        // Verify updated values using getters
        assertEquals(2, user.getId(), "User ID should be updated.");
        assertEquals("jane_doe", user.getUsername(), "Username should be updated.");
        assertEquals("newpassword", user.getPassword(), "Password should be updated.");
        assertEquals("admin", user.getRole(), "Role should be updated.");
    }

    @Test
    public void testToString() {
        // Verify the toString() output
        String expectedOutput = "User{Userid=1, username='john_doe', role='user'}";
        assertEquals(expectedOutput, user.toString(), "toString() should produce the correct output.");
    }
}
