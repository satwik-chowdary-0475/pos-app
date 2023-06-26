package com.increff.pos.dao;

import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Repository
@Log4j
public class OrderItemDao extends AbstractDao{
    private static String SELECT_BY_ID = "select p from OrderItemPojo p where id=:id and orderId=:orderId";
    private static String SELECT_BY_PRODUCT_ID = "select p from OrderItemPojo p where orderId=:orderId and productId=:productId";
    private static String SELECT_ALL = "select p from OrderItemPojo p where orderId=:orderId";
    private static String DELETE_BY_ID = "delete from OrderItemPojo p where orderId=:orderId and id=:id";
    private static String DELETE_BY_ORDER_ID = "delete from OrderItemPojo p where orderId=:orderId";
    private static String SELECT_BY_DATE = "select SUM(p.quantity), SUM(p.quantity*p.sellingPrice) from OrderItemPojo p where  FUNCTION('DATE', p.createdAt)=:targetDate and orderId=:orderId";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo p){
        em.persist(p);
    }

    @Transactional
    public OrderItemPojo select(int orderId,int id){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ID, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        query.setParameter("id",id);
        return getSingle(query);
    }
    @Transactional
    public OrderItemPojo selectByProduct(int orderId,int productId){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_PRODUCT_ID, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        query.setParameter("productId",productId);
        return getSingle(query);
    }

    @Transactional
    public Object[] selectByDate(int orderId){
        TypedQuery<Object[]> query = em.createQuery(SELECT_BY_DATE,Object[].class);
        Date today = new Date(System.currentTimeMillis());
        query.setParameter("targetDate",today);
        query.setParameter("orderId",orderId);
        Object[] result =  query.getSingleResult();
        return result;
    }
    @Transactional
    public List<OrderItemPojo> selectAll(int orderId){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL, OrderItemPojo.class);
        query.setParameter("orderId",orderId);
        return query.getResultList();
    }

    @Transactional
    public int delete(int orderId,int id){
        Query query = em.createQuery(DELETE_BY_ID);
        query.setParameter("id", id);
        query.setParameter("orderId",orderId);
        return query.executeUpdate();
    }

    @Transactional
    public int delete(int orderId){
        Query query = em.createQuery(DELETE_BY_ORDER_ID);
        query.setParameter("orderId",orderId);
        return query.executeUpdate();
    }

}
