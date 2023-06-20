package com.increff.pos.dao;

import com.increff.pos.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class InventoryDao extends AbstractDao{

    private static String DELETE_BY_ID = "delete from InventoryPojo p where id=:id";
    private static String SELECT_BY_ID = "select p from InventoryPojo p where id=:id";
    private static String SELECT_ALL = "select p from InventoryPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo p){
        em.persist(p);
    }

    @Transactional
    public int delete(int id){
        Query query = em.createQuery(DELETE_BY_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Transactional
    public InventoryPojo select(int id){
        TypedQuery<InventoryPojo> query = getQuery(SELECT_BY_ID, InventoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    @Transactional
    public List<InventoryPojo> selectAll(){
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
        return query.getResultList();
    }
}
