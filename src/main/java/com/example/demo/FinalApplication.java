package com.example.demo;

import com.example.demo.config.AppConfig;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.SubLibrary;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import com.example.demo.service.SubLibraryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class FinalApplication {

	public static void main(String[] args) {

		SpringApplication.run(FinalApplication.class, args);
	}

}
