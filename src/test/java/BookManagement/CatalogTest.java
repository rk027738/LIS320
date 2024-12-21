package BookManagement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;

public class CatalogTest {
    private Connection connection;
    private Catalog catalog;

    @BeforeEach
    public void setUp() throws SQLException {
        // Create an in-memory MySQL database (or test schema)
        connection = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7752156",
                "sql7752156",
                "NmwMfs5UnR");
        catalog = new Catalog(connection);

        // Clear the books table before each test
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("SET foreign_key_checks = 0"); // Disable constraints
            stmt.execute("DROP TABLE IF EXISTS books");
            stmt.execute("SET foreign_key_checks = 1"); // Re-enable constraints

            stmt.execute("CREATE TABLE books (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255) NOT NULL , " +
                    "author VARCHAR(255) NOT NULL , " +
                    "is_available BOOLEAN NOT NULL )");
        }

    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Close the database connection after each test
        connection.close();
    }

    @Test
    public void testAddBook() {
        Book book = new Book(0, "1984", "George Orwell", true);
        catalog.addBook(book);

        List<Book> books = catalog.getBooks();
        assertEquals(1, books.size(), "There should be one book in the catalog.");
        assertEquals("1984", books.get(0).getTitle(), "Book title should match.");
        assertEquals("George Orwell", books.get(0).getAuthor(), "Book author should match.");
    }

    @Test
    public void testRemoveBook() {
        Book book = new Book(0, "1984", "George Orwell", true);
        catalog.addBook(book);

        // Verify book exists
        List<Book> books = catalog.getBooks();
        assertEquals(1, books.size());

        // Remove the book
        catalog.removeBook(books.get(0).getId());

        // Verify book was removed
        books = catalog.getBooks();
        assertTrue(books.isEmpty(), "The book should be removed from the catalog.");
    }

    @Test
    public void testSearchBooks() {
        catalog.addBook(new Book(0, "1984", "George Orwell", true));
        catalog.addBook(new Book(0, "Brave New World", "Aldous Huxley", true));

        List<Book> results = catalog.searchBooks("1984");
        assertEquals(1, results.size(), "Search should return one book.");
        assertEquals("1984", results.get(0).getTitle(), "Search result should match the title.");
    }

    @Test
    public void testGetBooks() {
        catalog.addBook(new Book(0, "1984", "George Orwell", true));
        catalog.addBook(new Book(0, "Brave New World", "Aldous Huxley", true));

        List<Book> books = catalog.getBooks();
        assertEquals(2, books.size(), "There should be two books in the catalog.");
    }
}
