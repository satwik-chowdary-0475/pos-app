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
    public void insert(ProductPojo productPojo) throws ApiException {
        ProductPojo existingProductPojo = productDao.select(productPojo.getBarcode());
        if(existingProductPojo != null){
            throw new ApiException("Product with same barcode exists!!");
        }
        productDao.insert(productPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id,ProductPojo updatedProductPojo) throws ApiException{
        ProductPojo existingProductPojo = productDao.select(id);
        ProductPojo productPojoCheck = productDao.select(updatedProductPojo.getBarcode());
        if(productPojoCheck!=null && existingProductPojo != productPojoCheck){
            throw new ApiException("Product with same barcode exists!!");
        }
        existingProductPojo.setBarcode(updatedProductPojo.getBarcode());
        existingProductPojo.setName(updatedProductPojo.getName());
        existingProductPojo.setMrp(updatedProductPojo.getMrp());
        existingProductPojo.setBrandCategory(updatedProductPojo.getBrandCategory());
    }


    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo select(int id) throws ApiException {
        ProductPojo productPojo = productDao.select(id);
        if(productPojo == null){
            throw new ApiException("Product with given id doesn't exist!!");
        }
        return productPojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo select(String barcode) throws ApiException {
        ProductPojo productPojo = productDao.select(barcode);
        if(productPojo == null){
            throw new ApiException("Product with given barcode doesn't exist!!");
        }
        return productPojo;
    }

    @Transactional
    public List<ProductPojo> selectAll(){
        return productDao.selectAll();
    }

    @Transactional
    public List<ProductPojo> selectByBrandId(int brandCategoryId){
        return productDao.selectByBrandId(brandCategoryId);
    }
}
