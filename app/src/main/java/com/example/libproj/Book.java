package com.example.libproj;

public class Book {
    int id;
    String book, gen, author;

    public Book(int id, String book, String genre, String author) {
        this.id = id;
        this.book = book;
        this.gen = genre;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getBook() {
        return book;
    }

    public String getGenre() {
        return gen;
    }

    public String getAuthor() {
        return author;
    }
}
