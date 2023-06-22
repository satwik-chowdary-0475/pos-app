package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private InventoryService inventoryService;

    @Transactional
    public void insert(OrderItemPojo orderItemPojo) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.select(orderItemPojo.getProductId());
        int requiredQuantity = orderItemPojo.getQuantity();
        int inventoryQuantity = inventoryPojo.getQuantity();
        OrderItemPojo orderExists = orderItemDao.selectByProduct(orderItemPojo.getOrderId(),orderItemPojo.getProductId());
        if(orderExists != null){
            requiredQuantity+=orderExists.getQuantity();
            if(requiredQuantity>inventoryQuantity){
                throw new ApiException("Insufficient inventory for the product!!!");
            }

            orderExists.setQuantity(requiredQuantity);
        }
        else{
            orderItemDao.insert(orderItemPojo);
        }

    }

    @Transactional
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

    @Transactional
    public void update(int orderId,int id,OrderItemPojo p) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.select(orderId,id);
        if(orderItemPojo == null){
            throw new ApiException("Cannot update as order item doesn't exist!!");
        }
        InventoryPojo inventoryPojo = inventoryService.select(orderItemPojo.getProductId());
        int requiredQuantity = p.getQuantity();
        int inventoryQuantity = inventoryPojo.getQuantity();
        if(requiredQuantity > inventoryQuantity){
            throw new ApiException("Insufficient inventory for the product!!!");
        }
        orderItemPojo.setQuantity(requiredQuantity);
    }

    @Transactional
    public void delete(int orderId,int id){
        orderItemDao.delete(orderId,id);
    }

    @Transactional
    public void deleteByOrder(int orderId){
        orderItemDao.delete(orderId);
    }

}
