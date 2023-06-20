package com.increff.pos.service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    static Logger logger = Logger.getLogger(ProductService.class);
    @Autowired
    private ProductDao dao;

    @Autowired
    private BrandService brandService;

    @Transactional(rollbackOn = ApiException.class)
    public void insert(ProductPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id,ProductPojo p){
        ProductPojo productPojo = dao.select(id);
        productPojo.setName(p.getName());
        productPojo.setMrp(p.getMrp());
        productPojo.setBrandCategory(p.getBrandCategory());
    }


    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo select(int id) throws ApiException {
        ProductPojo p = dao.select(id);
        return p;
    }
    @Transactional
    public List<ProductPojo> selectAll(){
        return dao.selectAll();
    }
}
