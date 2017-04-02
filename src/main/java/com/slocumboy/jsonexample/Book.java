package com.slocumboy.jsonexample;

/**
 * Created by davidrho on 4/2/17.
 */
public class Book {

    private final String bookId;

    private final String name;


    private final String author;

    public Book(String bookId, String name, String author) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
    }

    public String getBookId() {
        return bookId;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
}
