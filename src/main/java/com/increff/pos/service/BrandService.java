package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.util.BrandServiceUtil;
import com.increff.pos.util.StringUtil;
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
        dao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id,BrandPojo p) throws ApiException {
        BrandPojo brandPojo = dao.select(id);
        BrandServiceUtil.validate(brandPojo);
        brandPojo.setBrand(p.getBrand());
        brandPojo.setCategory(p.getCategory());
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo select(int id) throws ApiException {
        BrandPojo p = dao.select(id);
        BrandServiceUtil.validate(p);
        return p;
    }

    @Transactional
    public List<BrandPojo> selectAll(){
        return dao.selectAll();
    }


}
