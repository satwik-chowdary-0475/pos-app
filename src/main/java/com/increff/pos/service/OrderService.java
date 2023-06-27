package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderPojo;
import lombok.extern.log4j.Log4j;
import org.apache.xpath.operations.Or;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Service
@Log4j
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional
    public void insert(OrderPojo orderPojo){
        orderDao.insert(orderPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id,OrderPojo orderPojo) throws ApiException {
        OrderPojo existingOrderPojo = orderDao.select(id);
        if(existingOrderPojo == null){
            throw new ApiException("Cannot update order as order doesn't exist!!");
        }
        existingOrderPojo.setTime(orderPojo.getTime());
        existingOrderPojo.setStatus(orderPojo.getStatus());
        existingOrderPojo.setCustomerName(orderPojo.getCustomerName());
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderPojo select(int id) throws ApiException{
        OrderPojo orderPojo = orderDao.select(id);
        if(orderPojo == null){
            throw new ApiException("Order doesn't exist!!");
        }
        return orderPojo;
    }

    @Transactional
    public List<OrderPojo> selectAll(){
        return orderDao.selectAll();
    }

    @Transactional
    public List<OrderPojo> selectByDate(Date startTime,Date endTime){
        return orderDao.selectByDate(startTime,endTime);
    }

    @Transactional
    public void delete(int id){
        orderDao.delete(id);
    }

    @Transactional
    public void invoice(int id){
        OrderPojo orderPojo = orderDao.select(id);
        orderPojo.setStatus("INVOICED");
    }
}
