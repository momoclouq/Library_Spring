package com.example.demo.service;

import com.example.demo.model.SubLibrary;
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
import java.util.List;

@Transactional
@Service
public class SubLibraryService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //CRUD operations
    public int saveSubLibrary(SubLibrary library){
        sessionFactory.getCurrentSession().save(library);
        return library.getId();
    }

    public void updateSubLibary(SubLibrary library){
        sessionFactory.getCurrentSession().update(library);
    }

    public SubLibrary getSubLibraryById(int id){
        return (SubLibrary) sessionFactory.getCurrentSession().get(SubLibrary.class, id);
    }

    //search API
    //search by subject
    public List<SubLibrary> getSubLibraryBySubject(String subject, boolean ascending){
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery(SubLibrary.class);
        Root<SubLibrary> root = criteria.from(SubLibrary.class);
        criteria.select(root).where(builder.equal(root.get("subject"), subject));
        if (ascending) criteria.orderBy(builder.asc(root.get("subject")));
        else criteria.orderBy(builder.desc(root.get("subject")));
        Query query = sessionFactory.getCurrentSession().createQuery(criteria);
        return query.getResultList();
    }
}
