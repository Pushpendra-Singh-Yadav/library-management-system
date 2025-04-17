package com.library.management;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.library.management.models.Admin;
import com.library.management.models.Book;
import com.library.management.models.LibraryUser;
import com.library.management.models.User;

public class Main {
    private static Library library;
    private static Scanner scanner;
    private static User currentUser;

    public static void main(String[] args) {
        library = new Library();
        scanner = new Scanner(System.in);

        // Add default admin user if none exists
        if (library.getUserByUsername("admin") == null) {
            Admin admin = new Admin(
                "admin1",
                "admin",
                "admin123",
                "System Administrator",
                "admin@library.com"
            );
            library.addUser(admin);
        }

        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else if (currentUser instanceof Admin) {
                showAdminMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid option!");
        }
    }

    private static void showAdminMenu() {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. Add Book");
        System.out.println("2. Remove Book");
        System.out.println("3. View All Books");
        System.out.println("4. View All Users");
        System.out.println("5. View Statistics");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                addBook();
                break;
            case 2:
                removeBook();
                break;
            case 3:
                viewAllBooks();
                break;
            case 4:
                viewAllUsers();
                break;
            case 5:
                viewStatistics();
                break;
            case 6:
                logout();
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private static void showUserMenu() {
        System.out.println("\n=== User Menu ===");
        System.out.println("1. Search Books");
        System.out.println("2. View Available Books");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. View My Borrowed Books");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                searchBooks();
                break;
            case 2:
                viewAllBooks();
                break;
            case 3:
                borrowBook();
                break;
            case 4:
                returnBook();
                break;
            case 5:
                viewMyBorrowedBooks();
                break;
            case 6:
                logout();
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (library.authenticateUser(username, password)) {
            currentUser = library.getUserByUsername(username);
            System.out.println("Welcome, " + currentUser.getName() + "!");
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    private static void register() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (library.getUserByUsername(username) != null) {
            System.out.println("Username already exists!");
            return;
        }

        String userId = UUID.randomUUID().toString();
        LibraryUser newUser = new LibraryUser(userId, username, password, name, email);
        library.addUser(newUser);
        System.out.println("Registration successful! Please login.");
    }

    private static void logout() {
        currentUser = null;
        System.out.println("Logged out successfully!");
    }

    private static void addBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Year: ");
        int year = scanner.nextInt();
        System.out.print("Total Copies: ");
        int totalCopies = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Book book = new Book(isbn, title, author, genre, year, totalCopies);
        if (library.addBook(book)) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Book with this ISBN already exists!");
        }
    }

    private static void removeBook() {
        System.out.print("Enter ISBN of book to remove: ");
        String isbn = scanner.nextLine();

        if (library.removeBook(isbn)) {
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Book not found!");
        }
    }

    private static void viewAllBooks() {
        System.out.println("\nAll Books:");
        System.out.println("Sort by (title/author/year): ");
        String sortBy = scanner.nextLine();
        
        List<Book> books = library.getSortedBooks(sortBy);
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private static void viewAllUsers() {
        System.out.println("\nAll Users:");
        for (User user : library.getAllUsers()) {
            System.out.println(user);
        }
    }

    private static void searchBooks() {
        System.out.println("Search by:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Genre");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine();

        List<Book> results;
        switch (choice) {
            case 1:
                results = library.findBooksByTitle(searchTerm);
                break;
            case 2:
                results = library.findBooksByAuthor(searchTerm);
                break;
            case 3:
                results = library.findBooksByGenre(searchTerm);
                break;
            default:
                System.out.println("Invalid option!");
                return;
        }

        if (results.isEmpty()) {
            System.out.println("No books found!");
        } else {
            System.out.println("\nSearch Results:");
            for (Book book : results) {
                System.out.println(book);
            }
        }
    }

    private static void borrowBook() {
        if (!(currentUser instanceof LibraryUser)) {
            System.out.println("Only library users can borrow books!");
            return;
        }

        System.out.print("Enter ISBN of book to borrow: ");
        String isbn = scanner.nextLine();

        if (library.borrowBook(currentUser.getUserId(), isbn)) {
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Unable to borrow book. It might be unavailable or you've reached your limit.");
        }
    }

    private static void returnBook() {
        if (!(currentUser instanceof LibraryUser)) {
            System.out.println("Only library users can return books!");
            return;
        }

        System.out.print("Enter ISBN of book to return: ");
        String isbn = scanner.nextLine();

        if (library.returnBook(currentUser.getUserId(), isbn)) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Unable to return book. Make sure you have borrowed this book.");
        }
    }

    private static void viewMyBorrowedBooks() {
        if (!(currentUser instanceof LibraryUser)) {
            System.out.println("Only library users can view borrowed books!");
            return;
        }

        LibraryUser user = (LibraryUser) currentUser;
        List<Book> borrowedBooks = user.getBorrowedBooks();

        if (borrowedBooks.isEmpty()) {
            System.out.println("You haven't borrowed any books.");
        } else {
            System.out.println("\nYour Borrowed Books:");
            for (Book book : borrowedBooks) {
                System.out.println(book);
            }
        }
    }

    private static void viewStatistics() {
        System.out.println("\n=== Library Statistics ===");
        
        Book mostBorrowed = library.getMostBorrowedBook();
        if (mostBorrowed != null) {
            System.out.println("Most Borrowed Book: " + mostBorrowed.getTitle());
        }

        LibraryUser mostActive = library.getMostActiveUser();
        if (mostActive != null) {
            System.out.println("Most Active User: " + mostActive.getName());
        }

        System.out.println("Total Books: " + library.getAllBooks().size());
        System.out.println("Total Users: " + library.getAllUsers().size());
    }
} 