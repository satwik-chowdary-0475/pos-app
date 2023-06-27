package com.increff.pos.dao;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderPojo;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.apache.xpath.operations.Or;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;


@Repository
@Log4j
public class OrderDao extends AbstractDao{


    // Delete order not required as of now.
    private static String DELETE_BY_ID = "delete from OrderPojo p where id=:id";
    private static String SELECT_BY_ID = "select p from OrderPojo p where id=:id";
    private static String SELECT_ALL = "select p from OrderPojo p";
    private static String SELECT_BY_DATE = "select p from OrderPojo p where p.status = 'INVOICED' and (FUNCTION('DATE',p.createdAt) BETWEEN :startTime and :endTime ) ";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo orderPojo){
        em.persist(orderPojo);
    }

    @Transactional
    public List<OrderPojo> selectByDate(Date startTime,Date endTime){
        Query query = em.createQuery(SELECT_BY_DATE);
        query.setParameter("startTime",startTime);
        query.setParameter("endTime",endTime);
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
