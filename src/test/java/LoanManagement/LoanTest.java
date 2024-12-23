package LoanManagement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class LoanTest {
    private Loan loan;

    @BeforeEach
    public void setUp() {
        // Create a loan object with test data
        LocalDateTime issueDate = LocalDateTime.of(2024, 6, 10, 10, 0); // June 10, 2024, 10:00 AM
        loan = new Loan(1, 101, 1001, issueDate, issueDate.plusMinutes(2),  0.0);
    }

    @Test
    public void testConstructor() {
        // Verify constructor initialization
        assertEquals(1, loan.getLoanId(), "Loan ID should be initialized correctly.");
        assertEquals(101, loan.getBookId(), "Book ID should be initialized correctly.");
        assertEquals(1001, loan.getUserId(), "User ID should be initialized correctly.");
        assertEquals(LocalDateTime.of(2024, 6, 10, 10, 0), loan.getIssueDate(), "Issue date should match.");
        assertEquals(LocalDateTime.of(2024, 6, 10, 10, 2), loan.getDueDate(), "Due date should be 2 minutes after issue date.");
        assertFalse(loan.isReturned(), "Loan should not be returned initially.");
    }

    @Test
    public void testSettersAndGetters() {
        LocalDateTime newIssueDate = LocalDateTime.of(2024, 6, 15, 12, 30);
        LocalDateTime newDueDate = newIssueDate.plusMinutes(2);

        loan.setLoanId(2);
        loan.setBookId(202);
        loan.setUserId(2002);
        loan.setIssueDate(newIssueDate);
        loan.setDueDate(newDueDate);
        loan.setReturned(true);
        loan.setLateFee(10.0);

        assertEquals(2, loan.getLoanId(), "Loan ID should be updated.");
        assertEquals(202, loan.getBookId(), "Book ID should be updated.");
        assertEquals(2002, loan.getUserId(), "User ID should be updated.");
        assertEquals(newIssueDate, loan.getIssueDate(), "Issue date should be updated.");
        assertEquals(newDueDate, loan.getDueDate(), "Due date should be updated.");
        assertTrue(loan.isReturned(), "Loan should be marked as returned.");
        assertEquals(10.0, loan.getLateFee(), "Late fee should be updated.");
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
        String expectedOutput = "Loan ID: 1, Book ID: 101, User ID: 1001, Issue Date: 2024-06-10T10:00, Due Date: 2024-06-10T10:02, Returned: false";
        assertEquals(expectedOutput, loan.toString(), "toString() should produce the correct output.");
    }
}
