package com.example.demo.service;

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
public class BookService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //CRUD operations
    public int saveBook(Book book){
        sessionFactory.getCurrentSession().save(book);
        return book.getId();
    }

    public void updateBook(Book book){
        sessionFactory.getCurrentSession().update(book);
    }

    public void deleteBook(Book book){
        sessionFactory.getCurrentSession().delete(book);
    }

    public Book getBookById(int id){
        return (Book) sessionFactory.getCurrentSession().get(Book.class, id);
    }

    //search API
    //search by name
    public List<Book> getBookByName(String name, boolean ascending){
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria.select(root).where(builder.equal(root.get("name"), name));
        if (ascending) criteria.orderBy(builder.asc(root.get("name")));
        else criteria.orderBy(builder.desc(root.get("name")));
        Query query = sessionFactory.getCurrentSession().createQuery(criteria);
        return query.getResultList();
    }

    //search by date - from date x to present
    public List<Book> getBookByCreatedDate(Date dateOfCreation, boolean ascending){
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria.select(root).where(builder.lessThanOrEqualTo(root.get("dateOfCreation"), dateOfCreation));
        if (ascending) criteria.orderBy(builder.asc(root.get("dateOfCreation")));
        else criteria.orderBy(builder.desc(root.get("dateOfCreation")));
        Query query = sessionFactory.getCurrentSession().createQuery(criteria);
        return query.getResultList();
    }
}
