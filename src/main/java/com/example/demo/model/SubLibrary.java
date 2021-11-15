package com.example.demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SubLibrary")
public class SubLibrary {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String subject;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Author> listOfAuthors;

    //constructor
    public SubLibrary(){

    }

    public List<Author> getListOfAuthors() {
        return listOfAuthors;
    }

    public void setListOfAuthors(List<Author> listOfAuthors) {
        this.listOfAuthors = listOfAuthors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}