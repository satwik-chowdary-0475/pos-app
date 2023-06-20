package com.increff.pos.dao;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderPojo;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao{

    // Delete order not required as of now.
//    private static String delete_id = "delete from OrderPojo p where id=:id";
    private static String select_id = "select p from OrderPojo p where id=:id";
    private static String select_all = "select p from OrderPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo p){
        em.persist(p);
    }

    @Transactional
    public OrderPojo select(int id){
        TypedQuery<OrderPojo> query = getQuery(select_id, OrderPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    @Transactional public List<OrderPojo> selectAll(){
        TypedQuery<OrderPojo> query = getQuery(select_all, OrderPojo.class);
        return query.getResultList();
    }

}
