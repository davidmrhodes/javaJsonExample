package com.slocumboy.jsonexample;

/**
 * Created by davidrho on 4/2/17.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/books")
    public Collection<Book> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Collection<Book> books = new ArrayList<>();

        books.add(new Book(Long.toString(counter.incrementAndGet()), "Harry Potter and the Sorcerer's Stone", "J.K. Rowling"));
        books.add(new Book(Long.toString(counter.incrementAndGet()), "Eragon", "Christopher Paolini"));
        books.add(new Book(Long.toString(counter.incrementAndGet()), "The Fellowship of the Ring", "J.R.R. Tolkien"));


        return books;
    }
}