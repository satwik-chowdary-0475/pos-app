package com.increff.pos.dao;


import com.increff.pos.pojo.BrandPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandDao extends AbstractDao{
    private static String SELECT_BY_ID = "select p from BrandPojo p where id=:id";
    private static String SELECT_ALL = "select p from BrandPojo p";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BrandPojo p){
        em.persist(p);
    }

    @Transactional
    public BrandPojo select(int id){
        TypedQuery<BrandPojo> query = getQuery(SELECT_BY_ID, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    @Transactional
    public List<BrandPojo> selectAll(){
        TypedQuery<BrandPojo> query = getQuery(SELECT_ALL, BrandPojo.class);
        return query.getResultList();
    }


}
