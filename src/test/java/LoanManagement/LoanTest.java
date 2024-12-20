package LoanManagement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class LoanTest {
    private Loan loan;

    @BeforeEach
    public void setUp() {
        // Create a loan object with test data
        loan = new Loan(1, 101, 1001, LocalDate.of(2024, 6, 10), null);
    }

    @Test
    public void testConstructor() {
        // Verify constructor initialization
        assertEquals(1, loan.getLoanId(), "Loan ID should be initialized correctly.");
        assertEquals(101, loan.getBookId(), "Book ID should be initialized correctly.");
        assertEquals(1001, loan.getUserId(), "User ID should be initialized correctly.");
        assertEquals(LocalDate.of(2024, 6, 10), loan.getIssueDate(), "Issue date should match.");
        assertEquals(LocalDate.of(2024, 6, 17), loan.getDueDate(), "Due date should be 7 days after issue date.");
        assertFalse(loan.isReturned(), "Loan should not be returned initially.");
    }

    @Test
    public void testSettersAndGetters() {
        loan.setLoanId(2);
        loan.setBookId(202);
        loan.setUserId(2002);
        loan.setIssueDate(LocalDate.of(2024, 6, 15));
        loan.setDueDate(LocalDate.of(2024, 6, 22));
        loan.setReturned(true);

        assertEquals(2, loan.getLoanId(), "Loan ID should be updated.");
        assertEquals(202, loan.getBookId(), "Book ID should be updated.");
        assertEquals(2002, loan.getUserId(), "User ID should be updated.");
        assertEquals(LocalDate.of(2024, 6, 15), loan.getIssueDate(), "Issue date should be updated.");
        assertEquals(LocalDate.of(2024, 6, 22), loan.getDueDate(), "Due date should be updated.");
        assertTrue(loan.isReturned(), "Loan should be marked as returned.");
    }

    @Test
    public void testReturnBook() {
        // Verify returnBook method functionality
        assertFalse(loan.isReturned(), "Loan should not be returned initially.");

        loan.returnBook();

        assertTrue(loan.isReturned(), "Loan should be marked as returned after calling returnBook().");
    }

    @Test
    public void testToString() {
        // Verify the toString method output
        String expectedOutput = "Loan ID: 1, Book ID: 101, User ID: 1001, Issue Date: 2024-06-10, Due Date: 2024-06-17, Returned: false";
        assertEquals(expectedOutput, loan.toString(), "toString() should produce the correct output.");
    }
}