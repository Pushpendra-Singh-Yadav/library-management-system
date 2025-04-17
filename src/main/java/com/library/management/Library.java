package com.library.management;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.management.models.Book;
import com.library.management.models.LibraryUser;
import com.library.management.models.User;

public class Library {
    private Map<String, Book> booksByIsbn;
    private Map<String, User> users;
    private static final String BOOKS_FILE = "books.json";
    private static final String USERS_FILE = "users.json";
    private final ObjectMapper objectMapper;

    public Library() {
        this.booksByIsbn = new HashMap<>();
        this.users = new HashMap<>();
        this.objectMapper = new ObjectMapper();
        loadData();
    }

    // Book Management Methods
    public boolean addBook(Book book) {
        if (!booksByIsbn.containsKey(book.getIsbn())) {
            booksByIsbn.put(book.getIsbn(), book);
            saveData();
            return true;
        }
        return false;
    }

    public boolean removeBook(String isbn) {
        if (booksByIsbn.containsKey(isbn)) {
            booksByIsbn.remove(isbn);
            saveData();
            return true;
        }
        return false;
    }

    public Book findBookByIsbn(String isbn) {
        return booksByIsbn.get(isbn);
    }

    public List<Book> findBooksByTitle(String title) {
        return booksByIsbn.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByAuthor(String author) {
        return booksByIsbn.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByGenre(String genre) {
        return booksByIsbn.values().stream()
                .filter(book -> book.getGenre().toLowerCase().equals(genre.toLowerCase()))
                .collect(Collectors.toList());
    }

    // User Management Methods
    public boolean addUser(User user) {
        if (!users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
            saveData();
            return true;
        }
        return false;
    }

    public User findUser(String userId) {
        return users.get(userId);
    }

    public boolean authenticateUser(String username, String password) {
        return users.values().stream()
                .anyMatch(user -> user.getUsername().equals(username) 
                        && user.getPassword().equals(password));
    }

    public User getUserByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    // Book Borrowing Methods
    public boolean borrowBook(String userId, String isbn) {
        User user = findUser(userId);
        Book book = findBookByIsbn(isbn);
        
        if (user instanceof LibraryUser && book != null) {
            LibraryUser libraryUser = (LibraryUser) user;
            boolean success = libraryUser.borrowBook(book);
            if (success) {
                saveData();
            }
            return success;
        }
        return false;
    }

    public boolean returnBook(String userId, String isbn) {
        User user = findUser(userId);
        Book book = findBookByIsbn(isbn);
        
        if (user instanceof LibraryUser && book != null) {
            LibraryUser libraryUser = (LibraryUser) user;
            boolean success = libraryUser.returnBook(book);
            if (success) {
                saveData();
            }
            return success;
        }
        return false;
    }

    // Data Persistence Methods
    private void loadData() {
        try {
            // Load books
            File booksFile = new File(BOOKS_FILE);
            if (booksFile.exists()) {
                List<Book> books = Arrays.asList(objectMapper.readValue(booksFile, Book[].class));
                booksByIsbn = books.stream().collect(
                    Collectors.toMap(Book::getIsbn, book -> book));
            }

            // Load users
            File usersFile = new File(USERS_FILE);
            if (usersFile.exists()) {
                List<User> userList = Arrays.asList(objectMapper.readValue(usersFile, User[].class));
                users = userList.stream().collect(
                    Collectors.toMap(User::getUserId, user -> user));
            }
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    private void saveData() {
        try {
            // Save books
            objectMapper.writeValue(new File(BOOKS_FILE), booksByIsbn.values());
            
            // Save users
            objectMapper.writeValue(new File(USERS_FILE), users.values());
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    // Sorting Methods
    public List<Book> getSortedBooks(String sortBy) {
        List<Book> books = new ArrayList<>(booksByIsbn.values());
        
        switch (sortBy.toLowerCase()) {
            case "title":
                books.sort(Comparator.comparing(Book::getTitle));
                break;
            case "author":
                books.sort(Comparator.comparing(Book::getAuthor));
                break;
            case "year":
                books.sort(Comparator.comparing(Book::getYear));
                break;
            default:
                books.sort(Comparator.comparing(Book::getIsbn));
        }
        
        return books;
    }

    // Statistics Methods
    public Book getMostBorrowedBook() {
        return booksByIsbn.values().stream()
                .max(Comparator.comparing(book -> book.getTotalCopies() - book.getAvailableCopies()))
                .orElse(null);
    }

    public LibraryUser getMostActiveUser() {
        return users.values().stream()
                .filter(user -> user instanceof LibraryUser)
                .map(user -> (LibraryUser) user)
                .max(Comparator.comparing(user -> user.getBorrowingHistory().size()))
                .orElse(null);
    }

    // Getters for collections
    public Collection<Book> getAllBooks() {
        return new ArrayList<>(booksByIsbn.values());
    }

    public Collection<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
} 