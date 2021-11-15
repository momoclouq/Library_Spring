package com.example.demo.service;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class AuthorService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //CRUD operations
    public int saveAuthor(Author author){
        sessionFactory.getCurrentSession().save(author);
        return author.getId();
    }

    public void updateAuthor(Author author){
        sessionFactory.getCurrentSession().update(author);
    }

    public void deleteAuthor(Author author){
        sessionFactory.getCurrentSession().delete(author);
    }

    public Author getAuthorById(int id){
        return (Author) sessionFactory.getCurrentSession().get(Author.class, id);
    }

    //search API
    //search by name
    public List<Author> getAuthorByName(String name, boolean ascending){
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery(Author.class);
        Root<Author> root = criteria.from(Author.class);
        criteria.select(root).where(builder.equal(root.get("name"), name));
        if (ascending) criteria.orderBy(builder.asc(root.get("name")));
        else criteria.orderBy(builder.desc(root.get("name")));
        Query query = sessionFactory.getCurrentSession().createQuery(criteria);
        return query.getResultList();
    }

    //search by date
    public List<Author> getAuthorByacademicCredentials(String academicCredentials, boolean ascending){
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery(Author.class);
        Root<Author> root = criteria.from(Author.class);
        criteria.select(root).where(builder.equal(root.get("academicCredentials"), academicCredentials));
        if (ascending) criteria.orderBy(builder.asc(root.get("academicCredentials")));
        else criteria.orderBy(builder.desc(root.get("academicCredentials")));
        Query query = sessionFactory.getCurrentSession().createQuery(criteria);
        return query.getResultList();
    }
}
