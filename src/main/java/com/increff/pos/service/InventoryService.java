package com.increff.pos.service;


import com.increff.pos.dao.InventoryDao;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao dao;


    @Transactional(rollbackOn = ApiException.class)
    public void insert(InventoryPojo inventoryPojo) throws ApiException {
        int id = inventoryPojo.getId();
        int quantity = inventoryPojo.getQuantity();
        InventoryPojo productExists = dao.select(id);
        if(productExists == null){
            InventoryPojo p = new InventoryPojo();
            p.setId(id);
            p.setQuantity(quantity);
            dao.insert(p);
        }
        else{
            int newQuantity = quantity + productExists.getQuantity();
            InventoryPojo p = dao.select(id);
            p.setQuantity(newQuantity);
        }
    }

    @Transactional
    public void delete(int id){
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo select(int id) throws ApiException {
        InventoryPojo p = dao.select(id);
        if(p == null){
            throw new ApiException("Product doesn't exist in Inventory!!");
        }
        return p;
    }

    @Transactional
    public List<InventoryPojo> selectAll(){
        return dao.selectAll();
    }

    @Transactional
    public void update(int id,int quantity){
        InventoryPojo p = dao.select(id);
        p.setQuantity(quantity);
    }



}
