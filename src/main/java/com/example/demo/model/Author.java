package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "author")

public class Author {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String academicCredentials;

    @Column
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> listOfBooks;

    public List<Book> getListOfBooks() {
        return listOfBooks;
    }

    //constructor
    public Author(){

    }

    public void setListOfBooks(List<Book> listOfBooks) {
        this.listOfBooks = listOfBooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcademicCredentials() {
        return academicCredentials;
    }

    public void setAcademicCredentials(String academicCredentials) {
        this.academicCredentials = academicCredentials;
    }
//    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch =
//            FetchType.EAGER)
//    private List<Answer> answers;


}
