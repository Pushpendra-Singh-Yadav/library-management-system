package com.library.management.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LibraryUser extends User {
    private static final String ROLE = "USER";
    private final List<Book> borrowedBooks;
    private final LinkedList<Book> borrowingHistory;
    private int maxBooksAllowed;

    public LibraryUser(String userId, String username, String password, String name, String email) {
        super(userId, username, password, name, email);
        this.borrowedBooks = new ArrayList<>();
        this.borrowingHistory = new LinkedList<>();
        this.maxBooksAllowed = 5; // Default maximum books allowed
    }

    @Override
    public String getRole() {
        return ROLE;
    }

    public List<Book> getBorrowedBooks() {
        return new ArrayList<>(borrowedBooks);
    }

    public LinkedList<Book> getBorrowingHistory() {
        return new LinkedList<>(borrowingHistory);
    }

    public boolean canBorrowBooks() {
        return borrowedBooks.size() < maxBooksAllowed;
    }

    public boolean borrowBook(Book book) {
        if (canBorrowBooks() && book.isAvailable()) {
            if (book.borrow()) {
                borrowedBooks.add(book);
                borrowingHistory.add(book);
                return true;
            }
        }
        return false;
    }

    public boolean returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            if (book.returnBook()) {
                borrowedBooks.remove(book);
                return true;
            }
        }
        return false;
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public void setMaxBooksAllowed(int maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }

    public int getCurrentBorrowedCount() {
        return borrowedBooks.size();
    }
} 