package com.increff.pos.dao;

import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{
    private static String SELECT_BY_ID = "select p from OrderItemPojo p where id=:id";
    private static String SELECT_ALL = "select p from OrderItemPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo p){
        em.persist(p);
    }

    @Transactional
    public OrderItemPojo select(int id){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ID, OrderItemPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    @Transactional
    public List<OrderItemPojo> selectAll(){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL, OrderItemPojo.class);
        return query.getResultList();
    }



}
