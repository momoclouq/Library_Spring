package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    //get author by id: for ease of usage
    @RequestMapping(path = "/authors/id/{id}", method = RequestMethod.GET)
    public Author getOrderById(@PathVariable("id") int id){
        return authorService.getAuthorById(id);
    }

    //add order:
    @RequestMapping(path = "/authors/add", method = RequestMethod.POST)
    public int addAuthor(@RequestBody Author author){
        return authorService.saveAuthor(author);
    }

    //update order: provide in the json both the new author and the id for the old author
    @RequestMapping(path = "/authors/update/{id}", method = RequestMethod.POST)
    public Author updateAuthor(@PathVariable("id") int id, @RequestBody Author newAuthor){
        try {
            Author currentAuthor = authorService.getAuthorById(id);
            if (currentAuthor != null){
                currentAuthor.setAcademicCredentials(newAuthor.getAcademicCredentials());
                currentAuthor.setName(newAuthor.getName());
                currentAuthor.setListOfBooks(newAuthor.getListOfBooks());
                authorService.updateAuthor(currentAuthor);
                return authorService.getAuthorById(id);
            }

            return null;
        } catch (Exception ex) {
            System.out.println("data corrupted");
            return null;
        }
    }

    //delete order by id: working
    @RequestMapping(path = "/authors/delete/{id}", method = RequestMethod.GET)
    public boolean deleteAuthors(@PathVariable("id") int id){
        Author currentAuthor = authorService.getAuthorById(id);
        if (currentAuthor != null){
            try{
                authorService.deleteAuthor(currentAuthor);
                return true;
            } catch (Exception ex){
                System.out.println("cannot delete the author as it was related to other entities");
            }
        }
        return false;
    }

    //get author by name: sort asc: "asc", desc: "desc"
    @RequestMapping(path = "/authors/name/{name}/{sort}", method = RequestMethod.GET)
    public List<Author> getAuthorByName(@PathVariable("name") String name, @PathVariable("sort") String sort){
        if (sort.equals("desc")) return authorService.getAuthorByName(name, false);
        return authorService.getAuthorByName(name, true);
    }

    //get author by academicCredentials: sort asc: "asc", desc: "desc"
    @RequestMapping(path = "/authors/credential/{credential}/{sort}", method = RequestMethod.GET)
    public List<Author> getAuthorByAcademicCredentials(@PathVariable("credential") String credential, @PathVariable("sort") String sort){
        if (sort.equals("desc")) return authorService.getAuthorByacademicCredentials(credential, false);
        return authorService.getAuthorByacademicCredentials(credential, true);
    }

//    @RequestMapping(path = "/authors/name/{name}", method = RequestMethod.GET)
//    public List<Order> getOrderByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
//        Calendar start = Calendar.getInstance();
//        start.setTime(date);
//        return orderService.getOrderByDate(start);
//    }
}
