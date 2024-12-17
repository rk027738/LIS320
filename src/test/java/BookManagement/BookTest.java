package BookManagement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookTest {
    private Book book;

    @BeforeEach
    public void setUp() {
        // Initialize the Book object before each test
        book = new Book(1, "The Hobbit", "J.R.R. Tolkien", true);
    }

    @Test
    public void testConstructor() {
        // Verify the constructor initializes fields correctly
        assertEquals(1, book.getId(), "Book ID should be initialized correctly.");
        assertEquals("The Hobbit", book.getTitle(), "Book title should be initialized correctly.");
        assertEquals("J.R.R. Tolkien", book.getAuthor(), "Book author should be initialized correctly.");
        assertTrue(book.isAvailable(), "Book availability should be initialized to true.");
    }

    @Test
    public void testGettersAndSetters() {
        // Test ID
        book.setId(2);
        assertEquals(2, book.getId(), "Book ID should be updated.");

        // Test Title
        book.setTitle("1984");
        assertEquals("1984", book.getTitle(), "Book title should be updated.");

        // Test Author
        book.setAuthor("George Orwell");
        assertEquals("George Orwell", book.getAuthor(), "Book author should be updated.");

        // Test Availability
        book.setAvailable(false);
        assertFalse(book.isAvailable(), "Book availability should be updated to false.");
    }

    @Test
    public void testToString() {
        // Verify the toString() output
        String expected = "Book [id=1, title=The Hobbit, author=J.R.R. Tolkien, available=true]";
        assertEquals(expected, book.toString(), "toString() method should return the correct string.");
    }
}
