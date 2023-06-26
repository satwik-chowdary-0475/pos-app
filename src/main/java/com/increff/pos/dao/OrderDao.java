package com.increff.pos.dao;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderPojo;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Repository
@Log4j
public class OrderDao extends AbstractDao{


    // Delete order not required as of now.
    private static String DELETE_BY_ID = "delete from OrderPojo p where id=:id";
    private static String SELECT_BY_ID = "select p from OrderPojo p where id=:id";
    private static String SELECT_ALL = "select p from OrderPojo p";
    private static String SELECT_BY_DATE = "select p from OrderPojo p where DATE(createdAt)=:targetDate and p.status = 'INVOICED' ";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo p){
        em.persist(p);
    }

    @Transactional
    public List<OrderPojo> selectByDate(){
        Query query = em.createQuery(SELECT_BY_DATE);
        Date today = new Date(System.currentTimeMillis());
        query.setParameter("targetDate",today);
        return query.getResultList();
    }
    @Transactional
    public OrderPojo select(int id){
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_ID, OrderPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    @Transactional
    public List<OrderPojo> selectAll(){
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL, OrderPojo.class);
        return query.getResultList();
    }

    @Transactional
    public int delete(int id){
        Query query = em.createQuery(DELETE_BY_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }


}
