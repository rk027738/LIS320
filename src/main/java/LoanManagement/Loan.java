package LoanManagement;

import java.time.LocalDateTime;

public class Loan {
    private int loanId;
    private int bookId;
    private int userId;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private boolean isReturned;
    private double lateFee;

    // Constructor to create a new loan record
    public Loan(int loanId, int bookId, int userId, LocalDateTime issueDate, LocalDateTime dueDate, double lateFee) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = issueDate;
        this.dueDate = issueDate.plusMinutes(2); // fixed loan duration to 7 days to prevent manual setting
        this.isReturned = false;// Default is that the book hasn't been returned
        this.lateFee = lateFee; // Initializing late fee
    }

    // Getters and setters
    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    // Method to mark the loan as returned
    public void returnBook() {
        this.isReturned = true;
        System.out.println("Loan " + loanId + " for book ID " + bookId + " has been returned.");
    }

    public double getLateFee() {
        return lateFee;
    }

    public void setLateFee(double lateFee) {
        this.lateFee = lateFee;
    }

    @Override
    public String toString() {
        return "Loan ID: " + loanId + ", Book ID: " + bookId + ", User ID: " + userId +
               ", Issue Date: " + issueDate + ", Due Date: " + dueDate + ", Returned: " + isReturned + ", Late Fee: " + lateFee;
    }


}