package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.pojo.BrandPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class BrandService {
    static Logger logger = Logger.getLogger(BrandService.class);
    @Autowired
    private BrandDao dao;

    @Transactional(rollbackOn = ApiException.class)
    public void insert(BrandPojo p) throws ApiException {
        BrandPojo brandPojo = dao.select(p.getBrand(),p.getCategory());
        if(brandPojo != null) throw new ApiException("brand-category pair already exist!!");
        dao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id,BrandPojo p) throws ApiException {
        BrandPojo brandPojo = dao.select(id);
        if(brandPojo == null) throw new ApiException("Cannot update as brand item doesn't exist!!");
        BrandPojo brandPojo1 = dao.select(p.getBrand(),p.getCategory());
        if(brandPojo1 != null && brandPojo!=brandPojo1) throw new ApiException("brand-category pair already exist!!");
        brandPojo.setBrand(p.getBrand());
        brandPojo.setCategory(p.getCategory());
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo select(int id) throws ApiException {
        BrandPojo brandPojo = dao.select(id);
        if(brandPojo == null) throw new ApiException("brand item doesn't exist!!");
        return brandPojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo select(String brand,String category) throws ApiException {
        BrandPojo brandPojo = dao.select(brand,category);
        if(brandPojo == null) throw new ApiException("brand item with given name-category doesn't exist!!");
        return brandPojo;
    }


    @Transactional
    public List<BrandPojo> selectAll(){
        return dao.selectAll();
    }

}
