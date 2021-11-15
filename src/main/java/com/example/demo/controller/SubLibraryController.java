package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.SubLibrary;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import com.example.demo.service.SubLibraryService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class SubLibraryController {

    @Autowired
    private SubLibraryService subLibraryService;

    //get library by id: for ease of usage
    @RequestMapping(path = "/libraries/id/{id}", method = RequestMethod.GET)
    public SubLibrary getLibraryById(@PathVariable("id") int id){
        return subLibraryService.getSubLibraryById(id);
    }

    //add library:
    @RequestMapping(path = "/libraries/add", method = RequestMethod.POST)
    public int addLibrary(@RequestBody SubLibrary library){
        return subLibraryService.saveSubLibrary(library);
    }

    //update library: provide in the json both the new library and the id for the old library
    @RequestMapping(path = "/libraries/update/{id}", method = RequestMethod.POST)
    public SubLibrary updateSubLibrary(@PathVariable("id") int id, @RequestBody SubLibrary newLibrary){
        try {
            SubLibrary currentLibrary = subLibraryService.getSubLibraryById(id);
            if (currentLibrary != null){
                currentLibrary.setSubject(newLibrary.getSubject());
                currentLibrary.setListOfAuthors(newLibrary.getListOfAuthors());
                subLibraryService.updateSubLibary(currentLibrary);
                return subLibraryService.getSubLibraryById(id);
            }

            return null;
        } catch (Exception ex) {
            System.out.println("data corrupted");
            return null;
        }
    }

    //get author by subject: sort asc: "asc", desc: "desc"
    @RequestMapping(path = "/libraries/subject/{subject}/{sort}", method = RequestMethod.GET)
    public List<SubLibrary> getSubLibraryBySubject(@PathVariable("subject") String subject, @PathVariable("sort") String sort){
        if (sort.equals("desc")) return subLibraryService.getSubLibraryBySubject(subject, false);
        return subLibraryService.getSubLibraryBySubject(subject, true);
    }
}
