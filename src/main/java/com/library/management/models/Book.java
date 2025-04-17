package com.library.management.models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class Book implements Serializable {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private int year;
    private int totalCopies;
    private int availableCopies;
    private final Queue<String> waitingList;  // Queue of user IDs waiting for the book

    public Book(String isbn, String title, String author, String genre, int year, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.waitingList = new LinkedList<>();
    }

    // Getters and Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    // Book operations
    public boolean isAvailable() {
        return availableCopies > 0;
    }

    public void addToWaitingList(String userId) {
        waitingList.offer(userId);
    }

    public String getNextWaitingUser() {
        return waitingList.poll();
    }

    public boolean hasWaitingList() {
        return !waitingList.isEmpty();
    }

    public boolean borrow() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }

    public boolean returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Book{ISBN='%s', Title='%s', Author='%s', Genre='%s', Year=%d, Available=%d/%d}",
                isbn, title, author, genre, year, availableCopies, totalCopies);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
} 