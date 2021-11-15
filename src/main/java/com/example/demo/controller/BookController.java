package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    //get book by id: for ease of usage
    @RequestMapping(path = "/books/id/{id}", method = RequestMethod.GET)
    public Book getBookById(@PathVariable("id") int id){
        return bookService.getBookById(id);
    }

    //add order:
    @RequestMapping(path = "/books/add", method = RequestMethod.POST)
    public int addBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    //update book: provide in the json both the new book and the id for the old book
    @RequestMapping(path = "/books/update/{id}", method = RequestMethod.POST)
    public Book updateBook(@PathVariable("id") int id, @RequestBody Book newBook){
        try {
            Book currentBook = bookService.getBookById(id);
            if (currentBook != null){
                currentBook.setName(newBook.getName());
                currentBook.setDateOfCreation(newBook.getDateOfCreation());
                currentBook.setAuthor(newBook.getAuthor());
                bookService.updateBook(currentBook);
                return bookService.getBookById(id);
            }

            return null;
        } catch (Exception ex) {
            System.out.println("data corrupted");
            return null;
        }
    }

    //delete order by id: working
    @RequestMapping(path = "/books/delete/{id}", method = RequestMethod.GET)
    public boolean deleteBook(@PathVariable("id") int id){
        Book currentBook = bookService.getBookById(id);
        if (currentBook != null){
            try{
                bookService.deleteBook(currentBook);
                return true;
            } catch (Exception ex){
                System.out.println("cannot delete the book as it was related to other entities");
            }
        }
        return false;
    }

    //get author by name: sort asc: "asc", desc: "desc"
    @RequestMapping(path = "/books/name/{name}/{sort}", method = RequestMethod.GET)
    public List<Book> getBookByName(@PathVariable("name") String name, @PathVariable("sort") String sort){
        if (sort.equals("desc")) return bookService.getBookByName(name, false);
        return bookService.getBookByName(name, true);
    }

    //get author by academicCredentials: sort asc: "asc", desc: "desc"
    @RequestMapping(path = "/authors/date/{dateOfCreation}/{sort}", method = RequestMethod.GET)
    public List<Book> getBookByDateOfCreation(@PathVariable("dateOfCreation") Date dateOfCreation, @PathVariable("sort") String sort){
        if (sort.equals("desc")) return bookService.getBookByCreatedDate(dateOfCreation, false);
        return bookService.getBookByCreatedDate(dateOfCreation, true);
    }
}
