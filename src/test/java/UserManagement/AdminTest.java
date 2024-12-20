package UserManagement;

import BookManagement.Book;
import BookManagement.Catalog;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {
    private Connection connection;
    private Admin admin;
    private Catalog catalog;

    @BeforeEach
    public void setUp() throws SQLException {
        // Initialize test database connection
        connection = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7752156",
                "sql7752156",
                "NmwMfs5UnR");
        admin = new Admin(connection);
        catalog = new Catalog(connection);

        // Clear tables before each test
        try (var stmt = connection.createStatement()) {
            stmt.execute("SET foreign_key_checks = 0");
            stmt.execute("TRUNCATE TABLE books");
            stmt.execute("TRUNCATE TABLE users");
            stmt.execute("SET foreign_key_checks = 1");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddBook() {
        Book book = new Book(0, "1984", "George Orwell", true);
        admin.addBook(catalog, book);

        var books = catalog.getBooks();
        assertEquals(1, books.size(), "There should be one book in the catalog.");
        assertEquals("1984", books.get(0).getTitle(), "Book title should match.");
    }

    @Test
    public void testRemoveBook() {
        Book book = new Book(0, "1984", "George Orwell", true);
        admin.addBook(catalog, book);

        var books = catalog.getBooks();
        assertEquals(1, books.size());

        admin.removeBook(catalog, books.get(0).getId());
        books = catalog.getBooks();

        assertTrue(books.isEmpty(), "The book should be removed from the catalog.");
    }

    @Test
    public void testAddUser() {
        admin.addUser("jane_doe", "securepass", "user");

        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery("SELECT COUNT(*) AS user_count FROM users WHERE username = 'jane_doe'")) {
            rs.next();
            assertEquals(1, rs.getInt("user_count"), "The user should be added to the database.");
        } catch (SQLException e) {
            fail("SQL query failed: " + e.getMessage());
        }
    }

    @Test
    public void testRemoveUser() {
        admin.addUser("jane_doe", "securepass", "user");

        // Verify user exists
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery("SELECT id FROM users WHERE username = 'jane_doe'")) {
            rs.next();
            int userId = rs.getInt("id");

            admin.removeUser(userId);

            // Verify user has been removed
            var rsCheck = stmt.executeQuery("SELECT COUNT(*) AS user_count FROM users WHERE id = " + userId);
            rsCheck.next();
            assertEquals(0, rsCheck.getInt("user_count"), "The user should be removed from the database.");
        } catch (SQLException e) {
            fail("SQL query failed: " + e.getMessage());
        }
    }
}