package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderPojo;
import lombok.extern.log4j.Log4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional
    public void insert(OrderPojo p){
        orderDao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id,OrderPojo p) throws ApiException {
        OrderPojo orderPojo = orderDao.select(id);
        if(orderPojo == null){
            throw new ApiException("Cannot update order as order doesn't exist!!");
        }
        orderPojo.setTime(p.getTime());
        orderPojo.setStatus(p.getStatus());
        orderPojo.setCustomerName(p.getCustomerName());
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
    public void delete(int id){
        orderDao.delete(id);
    }

    @Transactional
    public void invoice(int id){
        OrderPojo orderPojo = orderDao.select(id);
        orderPojo.setStatus("INVOICED");
    }
}
