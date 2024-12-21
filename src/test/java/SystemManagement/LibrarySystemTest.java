package SystemManagement;

import BookManagement.Book;
import UserManagement.User;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibrarySystemTest {

    private static LibrarySystem librarySystem;

    @BeforeAll
    static void setup() {
        librarySystem = new LibrarySystem();
    }

    @Test
    @Order(1)
    void testLoginSuccess() {
        User user = librarySystem.authenticate("root", "password");
        assertNotNull(user, "Login should be successful with correct credentials.");
        assertEquals("admin", user.getRole(), "User role should be admin.");
    }

    @Test
    @Order(2)
    void testLoginFailure() {
        User user = librarySystem.authenticate("wrongUser", "wrongPass");
        assertNull(user, "Login should fail with incorrect credentials.");
    }

    @Test
    @Order(3)
//    void testAddBook() {
//        // Add a book
//        librarySystem.addBookManually(101, "Test Book", "Test Author");
//
//        // Fetch the catalog to validate
//        boolean bookExists = librarySystem.getCatalog().getBooks().stream()
//                .anyMatch(b -> b.getId() == 101 && b.getTitle().equals("Test Book") && b.getAuthor().equals("Test Author"));
//
//        assertTrue(bookExists, "Book should be added to the catalog.");
//    }
    void testAddBook() {
        // Add a book
        librarySystem.addBookManually("Test Book", "Test Author");

        // Fetch the catalog to validate
        boolean bookExists = librarySystem.getCatalog().getBooks().stream()
                .anyMatch(b -> b.getTitle().equals("Test Book") && b.getAuthor().equals("Test Author"));

        assertTrue(bookExists, "Book should be added to the catalog.");
    }



    @Test
    @Order(4)
    void testRemoveBook() {
        // Remove the book
        librarySystem.removeBookManually(1);

        // Validate that the book no longer exists
        boolean bookExists = librarySystem.getCatalog().getBooks().stream()
                .anyMatch(b -> b.getId() == 1);
        assertFalse(bookExists, "Book should be removed from the catalog.");
    }

    @Test
    @Order(5)
//    void testBorrowBook() {
//        // Add a new book
//        librarySystem.addBookManually ("Borrowable Book", "Test Author");
//
//        int testBookId = ;
//
//        // Borrow the book
//        assertDoesNotThrow(() -> librarySystem.borrowBook(2, 3), "Borrow operation should succeed.");
//
//        // Validate book availability
//        Optional<Book> book = librarySystem.getCatalog().getBooks().stream()
//                .filter(b -> b.getId() == 2).findFirst();
//        assertTrue(book.isPresent(), "Book should exist in the catalog.");
//        assertFalse(book.get().isAvailable(), "Book should be marked unavailable after borrowing.");
//    }
    void testBorrowBook() {
        // Add a new book
        librarySystem.addBookManually("Borrowable Book", "Test Author");

        // Retrieve the book ID based on the title and author
        Optional<Book> bookOptional = librarySystem.getCatalog().getBooks().stream()
                .filter(b -> b.getTitle().equals("Borrowable Book") && b.getAuthor().equals("Test Author"))
                .findFirst();

        // Ensure the book exists and retrieve its ID
        assertTrue(bookOptional.isPresent(), "Book should exist in the catalog.");
        int testBookId = bookOptional.get().getId();

        // Borrow the book
        assertDoesNotThrow(() -> librarySystem.borrowBook(testBookId, 3), "Borrow operation should succeed.");

        // Validate book availability
        Optional<Book> book = librarySystem.getCatalog().getBooks().stream()
                .filter(b -> b.getId() == testBookId).findFirst();
        assertTrue(book.isPresent(), "Book should exist in the catalog.");
        assertFalse(book.get().isAvailable(), "Book should be marked unavailable after borrowing.");
    }


    @Test
    @Order(6)
    void testReturnBook() {
        Optional<Book> bookOptional = librarySystem.getCatalog().getBooks().stream()
                .filter(b -> b.getTitle().equals("Borrowable Book") && b.getAuthor().equals("Test Author"))
                .findFirst();

        // Ensure the book exists and retrieve its ID
        int testBookId = bookOptional.get().getId();
        // Return the borrowed book
        assertDoesNotThrow(() -> librarySystem.returnBook(testBookId), "Return operation should succeed.");

        // Validate book availability
        Optional<Book> book = librarySystem.getCatalog().getBooks().stream()
                .filter(b -> b.getId() == testBookId).findFirst();
        assertTrue(book.isPresent(), "Book should exist in the catalog.");
        assertTrue(book.get().isAvailable(), "Book should be marked available after returning.");
    }

    @Test
    @Order(7)
    void testAddUser() {
        // Add a user directly
        String query = "INSERT INTO users (id, username, password, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = librarySystem.getConnection().prepareStatement(query)) {
            stmt.setInt(1, 200);
            stmt.setString(2, "testUser");
            stmt.setString(3, "testPass");
            stmt.setString(4, "user");
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("User addition failed due to database error.");
        }

        // Validate user login
        User user = librarySystem.authenticate("testUser", "testPass");
        assertNotNull(user, "User should be able to log in after being added.");
        assertEquals("user", user.getRole(), "User role should match.");
    }

    @Test
    @Order(8)
    void testRemoveUser() {
        // Remove the test user
        String query = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement stmt = librarySystem.getConnection().prepareStatement(query)) {
            stmt.setString(1, "testUser");
            int rows = stmt.executeUpdate();
            assertEquals(1, rows, "One user should be removed.");
        } catch (SQLException e) {
            fail("User removal failed due to database error.");
        }

        // Validate user login failure
        User user = librarySystem.authenticate("testUser", "testPass");
        assertNull(user, "User should not be able to log in after being removed.");
    }
}