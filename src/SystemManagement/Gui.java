package SystemManagement;

import BookManagement.Book;
import LoanManagement.Loan;
import UserManagement.User;
import UserManagement.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Gui {

    private LibrarySystem librarySystem;
    private JFrame frame;
    private User loggedInUser;

    public Gui(LibrarySystem librarySystem) {
        this.librarySystem = librarySystem;
        initializeLogin();
    }

    private void initializeLogin() {
        frame = new JFrame("Library Information System - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        frame.add(panel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User user = librarySystem.authenticate(username, password);
            if (user != null) {
                loggedInUser = user;
                frame.dispose();
                if (user instanceof Admin) {
                    initializeAdminMenu();
                } else {
                    initializeUserMenu();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    private void initializeAdminMenu() {
        frame = new JFrame("Library System - Admin Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JButton viewCatalogButton = new JButton("View Catalog");
        JButton searchBookButton = new JButton("Search Book");
        JButton addBookButton = new JButton("Add Book");
        JButton removeBookButton = new JButton("Remove Book");
        JButton viewLoansButton = new JButton("View Loans");
        JButton logoutButton = new JButton("Logout");

        panel.add(viewCatalogButton);
        panel.add(searchBookButton);
        panel.add(addBookButton);
        panel.add(removeBookButton);
        panel.add(viewLoansButton);
        panel.add(logoutButton);

        frame.add(panel);

        viewCatalogButton.addActionListener(e -> showCatalog());
        searchBookButton.addActionListener(e -> searchBook());
        addBookButton.addActionListener(e -> addBook());
        removeBookButton.addActionListener(e -> removeBook());
        viewLoansButton.addActionListener(e -> viewLoans());
        logoutButton.addActionListener(e -> logout());

        frame.setVisible(true);
    }

    private void initializeUserMenu() {
        frame = new JFrame("Library System - User Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton viewCatalogButton = new JButton("View Catalog");
        JButton searchBookButton = new JButton("Search Book");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton logoutButton = new JButton("Logout");

        panel.add(viewCatalogButton);
        panel.add(searchBookButton);
        panel.add(borrowBookButton);
        panel.add(returnBookButton);
        panel.add(logoutButton);

        frame.add(panel);

        viewCatalogButton.addActionListener(e -> showCatalog());
        searchBookButton.addActionListener(e -> searchBook());
        borrowBookButton.addActionListener(e -> borrowBook());
        returnBookButton.addActionListener(e -> returnBook());
        logoutButton.addActionListener(e -> logout());

        frame.setVisible(true);
    }

    private void logout() {
        loggedInUser = null;
        frame.dispose();
        initializeLogin();
    }

    private void showCatalog() {
        List<Book> books = librarySystem.getCatalog().getBooks();
        StringBuilder catalogDisplay = new StringBuilder("Books in the Catalog:\n\n");
        for (Book book : books) {
            catalogDisplay.append(book.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(frame, catalogDisplay.toString(), "Catalog", JOptionPane.INFORMATION_MESSAGE);
    }

    private void searchBook() {
        String keyword = JOptionPane.showInputDialog(frame, "Enter a keyword to search for books:", "Search Book", JOptionPane.QUESTION_MESSAGE);
        if (keyword != null && !keyword.trim().isEmpty()) {
            List<Book> results = librarySystem.getCatalog().searchBooks(keyword);
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No books found matching the keyword: " + keyword, "Search Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder resultDisplay = new StringBuilder("Search Results:\n\n");
                for (Book book : results) {
                    resultDisplay.append(book.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(frame, resultDisplay.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void addBook() {
        try {
            String idInput = JOptionPane.showInputDialog(frame, "Enter Book ID:", "Add Book", JOptionPane.QUESTION_MESSAGE);
            String title = JOptionPane.showInputDialog(frame, "Enter Book Title:", "Add Book", JOptionPane.QUESTION_MESSAGE);
            String author = JOptionPane.showInputDialog(frame, "Enter Book Author:", "Add Book", JOptionPane.QUESTION_MESSAGE);

            if (idInput != null && title != null && author != null) {
                int id = Integer.parseInt(idInput);
                librarySystem.addBookManually(id, title, author);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeBook() {
        try {
            String bookIdInput = JOptionPane.showInputDialog(frame, "Enter Book ID to remove:", "Remove Book", JOptionPane.QUESTION_MESSAGE);
            if (bookIdInput != null) {
                int bookId = Integer.parseInt(bookIdInput);
                librarySystem.removeBookManually(bookId);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid Book ID. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void borrowBook() {
        try {
            String bookIdInput = JOptionPane.showInputDialog(frame, "Enter Book ID to borrow:", "Borrow Book", JOptionPane.QUESTION_MESSAGE);
            if (bookIdInput != null) {
                int bookId = Integer.parseInt(bookIdInput);
                librarySystem.borrowBookManually(bookId, loggedInUser.getId());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid Book ID. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnBook() {
        try {
            String loanIdInput = JOptionPane.showInputDialog(frame, "Enter Loan ID to return:", "Return Book", JOptionPane.QUESTION_MESSAGE);
            if (loanIdInput != null) {
                int loanId = Integer.parseInt(loanIdInput);
                librarySystem.returnBookManually(loanId);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid Loan ID. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewLoans() {
        List<Loan> loans = librarySystem.getLoans();
        StringBuilder loansDisplay = new StringBuilder("Loans:\n\n");
        for (Loan loan : loans) {
            loansDisplay.append(loan.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(frame, loansDisplay.toString(), "Loans", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();
        new Gui(librarySystem);
    }
}


