package SystemManagement;

import UserManagement.Admin;
import UserManagement.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class GuiTest {
    private LibrarySystem librarySystem;
    private Gui gui;

    @BeforeEach
    public void setUp() {
        // Initialize the library system with its default constructor
        librarySystem = new LibrarySystem();
        gui = new Gui(librarySystem);
    }

    @Test
    public void testInitializeLogin() {
        SwingUtilities.invokeLater(() -> {
            // Test login GUI initialization
            gui.initializeLogin();
            JFrame loginFrame = gui.getFrame();
            assertNotNull(loginFrame, "Login frame should be initialized.");
            assertEquals("Library Information System - Login", loginFrame.getTitle(),
                    "Login frame title should match.");
        });
    }

    @Test
    public void testSuccessfulAdminLogin() {
        SwingUtilities.invokeLater(() -> {
            // Simulate admin login
            User admin = new Admin(1, "admin", "password", "admin");
            librarySystem.setLoggedInUser(admin);
            gui.setLoggedInUser(admin);
            gui.initializeAdminMenu();

            JFrame adminFrame = gui.getFrame();
            assertNotNull(adminFrame, "Admin menu frame should be initialized.");
            assertEquals("Library System - Admin Menu", adminFrame.getTitle(),
                    "Admin menu frame title should match.");
        });
    }

    @Test
    public void testSuccessfulUserLogin() {
        SwingUtilities.invokeLater(() -> {
            // Simulate user login
            User user = new User(2, "user", "password", "user");
            librarySystem.setLoggedInUser(user);
            gui.setLoggedInUser(user);
            gui.initializeUserMenu();

            JFrame userFrame = gui.getFrame();
            assertNotNull(userFrame, "User menu frame should be initialized.");
            assertEquals("Library System - User Menu", userFrame.getTitle(),
                    "User menu frame title should match.");
        });
    }

    @Test
    public void testShowCatalog() {
        SwingUtilities.invokeLater(() -> {
            // Simulate viewing catalog
            gui.showCatalog();
            assertNotNull(gui.getFrame(), "Catalog display should be initialized.");
        });
    }

    @Test
    public void testSearchBook() {
        SwingUtilities.invokeLater(() -> {
            // Simulate searching for a book
            gui.searchBook();
            assertNotNull(gui.getFrame(), "Search book GUI should be displayed.");
        });
    }

    @Test
    public void testLogout() {
        SwingUtilities.invokeLater(() -> {
            // Simulate logout functionality
            gui.logout();
            assertNull(gui.getLoggedInUser(), "Logged-in user should be null after logout.");
            });
    }
}