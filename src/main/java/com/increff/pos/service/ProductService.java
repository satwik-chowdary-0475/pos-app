package com.increff.pos.service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.ProductPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;
    @Transactional(rollbackOn = ApiException.class)
    public void insert(ProductPojo p) throws ApiException {
        productDao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id,ProductPojo p){
        ProductPojo productPojo = productDao.select(id);
        productPojo.setBarcode(p.getBarcode());
        productPojo.setName(p.getName());
        productPojo.setMrp(p.getMrp());
        productPojo.setBrandCategory(p.getBrandCategory());
    }


    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo select(int id) throws ApiException {
        ProductPojo p = productDao.select(id);
        if(p == null){
            throw new ApiException("Product with given id doesn't exist!!");
        }
        return p;
    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo select(String barcode) throws ApiException {
        ProductPojo p = productDao.select(barcode);
        if(p == null){
            throw new ApiException("Product with given barcode doesn't exist!!");
        }
        return p;
    }

    @Transactional
    public List<ProductPojo> selectAll(){
        return productDao.selectAll();
    }
}
