package com.increff.pos.service;


import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao dao;

    @Transactional
    public void insert(OrderPojo p){
        dao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderPojo select(int id) throws ApiException {
        OrderPojo p = dao.select(id);
        if(p == null){
            throw new ApiException("Order with given id doesn't exist!!");
        }
        return p;
    }

    @Transactional
    public List<OrderPojo> selectAll(){
        return dao.selectAll();
    }

}
