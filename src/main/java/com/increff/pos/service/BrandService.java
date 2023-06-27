package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.pojo.BrandPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class BrandService {
    static Logger logger = Logger.getLogger(BrandService.class);
    @Autowired
    private BrandDao brandDao;

    @Transactional(rollbackOn = ApiException.class)
    public void insert(BrandPojo newBrandPojo) throws ApiException {
        BrandPojo existingbrandPojo = brandDao.select(newBrandPojo.getBrand(),newBrandPojo.getCategory());
        if(existingbrandPojo != null) throw new ApiException("brand-category pair already exist!!");
        brandDao.insert(newBrandPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id,BrandPojo newBrandPojo) throws ApiException {
        BrandPojo existingbrandPojo = brandDao.select(id);
        if(existingbrandPojo == null) throw new ApiException("Cannot update as brand item doesn't exist!!");
        BrandPojo brandPojoCheck = brandDao.select(newBrandPojo.getBrand(),newBrandPojo.getCategory());
        if(brandPojoCheck != null && existingbrandPojo!=brandPojoCheck) throw new ApiException("brand-category pair already exist!!");
        existingbrandPojo.setBrand(newBrandPojo.getBrand());
        existingbrandPojo.setCategory(newBrandPojo.getCategory());
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo select(int id) throws ApiException {
        BrandPojo brandPojo = brandDao.select(id);
        if(brandPojo == null) throw new ApiException("brand item doesn't exist!!");
        return brandPojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo select(String brand,String category) throws ApiException {
        BrandPojo brandPojo = brandDao.select(brand,category);
        if(brandPojo == null) throw new ApiException("brand item with given name-category doesn't exist!!");
        return brandPojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<BrandPojo> selectByBrandCategory(String brand,String category){
        if(brand.equals("") && category.equals("")) {
            return brandDao.selectAll();
        }
        if(brand.equals("")) {
            return brandDao.selectByCategory(category);
        }
        if(category.equals("")) {
            return brandDao.selectByBrand(brand);
        }
        List<BrandPojo> brandPojoList = new ArrayList<BrandPojo>();
        BrandPojo brandPojo = brandDao.select(brand, category);
        brandPojoList.add(brandPojo);
        return brandPojoList;
    }


    @Transactional
    public List<BrandPojo> selectAll(){
        return brandDao.selectAll();
    }

}
