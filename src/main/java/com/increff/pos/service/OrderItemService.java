package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
@Log4j
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackOn = ApiException.class)
    public void insert(OrderItemPojo orderItemPojo,InventoryPojo inventoryPojo) throws ApiException {
        int requiredQuantity = orderItemPojo.getQuantity();
        int inventoryQuantity = inventoryPojo.getQuantity();
        OrderItemPojo orderExists = orderItemDao.selectByProduct(orderItemPojo.getOrderId(),orderItemPojo.getProductId());
        if(requiredQuantity>inventoryQuantity) {
            throw new ApiException("Insufficient inventory for the product!!!");
        }
        if(orderExists != null){
            orderExists.setQuantity(requiredQuantity+orderExists.getQuantity());
        }
        else{
            orderItemDao.insert(orderItemPojo);
        }
        inventoryPojo.setQuantity(inventoryQuantity-requiredQuantity);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderItemPojo select(int orderId,int id) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.select(orderId,id);
        if(orderItemPojo == null){
            throw new ApiException("Order item with given order id and id doesn't exist!!");
        }
        return orderItemPojo;
    }

    @Transactional
    public List<OrderItemPojo> selectAll(int orderId){
        return orderItemDao.selectAll(orderId);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int orderId,int id,OrderItemPojo p,InventoryPojo inventoryPojo) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.select(orderId,id);
        if(orderItemPojo == null){
            throw new ApiException("Cannot update as order item doesn't exist!!");
        }
        int requiredQuantity = p.getQuantity();
        int inventoryQuantity = inventoryPojo.getQuantity();
        if(requiredQuantity > inventoryQuantity){
            throw new ApiException("Insufficient inventory for the product!!!");
        }
        orderItemPojo.setQuantity(requiredQuantity);
        orderItemPojo.setSellingPrice(p.getSellingPrice());
        inventoryPojo.setQuantity(inventoryQuantity-requiredQuantity);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete (int orderId,int id,InventoryPojo inventoryPojo) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.select(orderId,id);
        if(orderItemPojo == null){
            throw new ApiException("Order item doesn't exist!!");
        }
        inventoryPojo.setQuantity(inventoryPojo.getQuantity() + orderItemPojo.getQuantity());
        orderItemDao.delete(orderId,id);
    }

    @Transactional
    public void deleteByOrder(int orderId,Map<InventoryPojo,Integer>inventoryPojoDict){
        for(Map.Entry<InventoryPojo,Integer> entry : inventoryPojoDict.entrySet()){
            InventoryPojo inventoryPojo = entry.getKey();
            int updatedQuantity = inventoryPojo.getQuantity() + entry.getValue();
            inventoryPojo.setQuantity(updatedQuantity);
        }
        orderItemDao.delete(orderId);
    }

}
