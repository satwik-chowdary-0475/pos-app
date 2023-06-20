package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

import org.apache.log4j.Logger;
@Service
public class OrderItemService {

    static Logger logger = Logger.getLogger(OrderItemService.class);
    @Autowired
    private OrderItemDao dao;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;



//    private static Timestamp timeStamp;
//    private static Date date;

    @Transactional(rollbackOn = ApiException.class)
    public void insert(OrderItemPojo p) throws ApiException {

        //Check in inventory
        int productId = p.getProductId();
        int quantity = p.getQuantity();
        InventoryPojo product = inventoryService.select(productId);
        logger.warn(product.getId());
        if(product == null || (product.getQuantity() < quantity)){
            throw new ApiException("No sufficient inventory for the product!!");
        }

        //Create an Order
        OrderPojo order = new OrderPojo();
//        date = new Date();
//        timeStamp = new Timestamp(date.getTime());
//        order.setTime(timeStamp);
        order.setTime(ZonedDateTime.now());
        orderService.insert(order);
        p.setOrderId(order.getId());
        //Create order item
        logger.warn("product id is : " + productId);
//        ProductPojo productPojo = productService.select(productId);
        logger.warn(p.getOrderId() + " " + p.getProductId() + " " + p.getId());
        dao.insert(p);

        // Reduce Inventory
        inventoryService.update(productId,(product.getQuantity() - quantity));
    }

    @Transactional
    public List<OrderItemPojo> selectAll(){
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderItemPojo select(int id) throws ApiException {
        OrderItemPojo p = dao.select(id);
        if(p == null){
            throw new ApiException("OrderItem with given id doesn't exist!!");
        }
        return p;
    }

    @Transactional
    public void update(OrderItemPojo p){
        OrderItemPojo item = dao.select(p.getId());
        item.setQuantity(p.getQuantity());
    }




}
