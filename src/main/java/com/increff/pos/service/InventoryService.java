package com.increff.pos.service;


import com.increff.pos.dao.InventoryDao;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryDao inventoryDao;

    static Logger logger = Logger.getLogger(InventoryService.class);
    @Transactional(rollbackOn = ApiException.class)
    public void insert(InventoryPojo inventoryPojo) throws ApiException {
        int id = inventoryPojo.getId();
        int quantity = inventoryPojo.getQuantity();
        InventoryPojo productExists = inventoryDao.select(id);
        if(productExists == null){
            InventoryPojo p = new InventoryPojo();
            p.setId(id);
            p.setQuantity(quantity);
            inventoryDao.insert(p);
        }
        else{
            int newQuantity = quantity + productExists.getQuantity();
            productExists.setQuantity(newQuantity);
        }
    }

    @Transactional
    public void delete(int id){
        inventoryDao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo select(int id) throws ApiException {
        InventoryPojo p = inventoryDao.select(id);
        if(p == null){
            throw new ApiException("Product with id not present in inventory!!");
        }
        return p;
    }

    @Transactional
    public List<InventoryPojo> selectAll(){
        return inventoryDao.selectAll();
    }

    @Transactional
    public void update(InventoryPojo inventoryPojo){
        InventoryPojo p = inventoryDao.select(inventoryPojo.getId());
        p.setQuantity(inventoryPojo.getQuantity());
    }

}
