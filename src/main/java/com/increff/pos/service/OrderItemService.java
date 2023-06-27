package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
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
        OrderItemPojo existingOrderPojo = orderItemDao.selectByProduct(orderItemPojo.getOrderId(),orderItemPojo.getProductId());
        if(requiredQuantity>inventoryQuantity) {
            throw new ApiException("Insufficient inventory for the product!!!");
        }
        if(existingOrderPojo != null){
            existingOrderPojo.setSellingPrice(orderItemPojo.getSellingPrice());
            existingOrderPojo.setQuantity(requiredQuantity+existingOrderPojo.getQuantity());
        }
        else{
            orderItemDao.insert(orderItemPojo);
        }
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderItemPojo select(int orderId,int id) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.select(orderId,id);
        if(orderItemPojo == null){
            throw new ApiException("Order item with given order id and id doesn't exist!!");
        }
        return orderItemPojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderItemPojo selectByProduct(int orderId,int productId){
        OrderItemPojo orderItemPojo = orderItemDao.selectByProduct(orderId,productId);
        return orderItemPojo;
    }

    @Transactional
    public List<OrderItemPojo> selectAll(int orderId){
        return orderItemDao.selectAll(orderId);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int orderId,int id,OrderItemPojo updatedOrderItemPojo,InventoryPojo inventoryPojo) throws ApiException{
        OrderItemPojo existingOrderItemPojo = orderItemDao.select(orderId,id);
        if(existingOrderItemPojo == null){
            throw new ApiException("Cannot update as order item doesn't exist!!");
        }
        int requiredQuantity = updatedOrderItemPojo.getQuantity();
        int inventoryQuantity = inventoryPojo.getQuantity() + existingOrderItemPojo.getQuantity();
        if(requiredQuantity > inventoryQuantity){
            throw new ApiException("Insufficient inventory for the product!!!");
        }
        existingOrderItemPojo.setQuantity(requiredQuantity);
        existingOrderItemPojo.setSellingPrice(updatedOrderItemPojo.getSellingPrice());
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete (int orderId,int id) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.select(orderId,id);
        if(orderItemPojo == null){
            throw new ApiException("Order item doesn't exist!!");
        }

        orderItemDao.delete(orderId,id);
    }

    @Transactional
    public void deleteByOrder(int orderId){
        orderItemDao.delete(orderId);
    }


}
