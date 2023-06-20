package com.increff.pos.controller;

import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class OrderApiController {

    @Autowired
    private OrderItemService orderItemService;

    @ApiOperation(value = "Creates an order")
    @RequestMapping(path = "/api/order",method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm form) throws ApiException {
        OrderItemPojo p = convert(form);
        orderItemService.insert(p);
    }

    @ApiOperation(value = "Get list of all order items")
    @RequestMapping(path = "/api/order",method = RequestMethod.GET)
    public List<OrderItemData> getAllOrderItems(){
        List<OrderItemPojo> list = orderItemService.selectAll();
        List<OrderItemData> dataList = new ArrayList<OrderItemData>();
        for(OrderItemPojo p : list){
            dataList.add(convert(p));
        }
        return dataList;
    }

    @ApiOperation(value = "Get details of an order item")
    @RequestMapping(path = "/api/order/{id}",method = RequestMethod.GET)
    public OrderItemPojo getOrderItem(@PathVariable int id) throws ApiException {
        OrderItemPojo p = orderItemService.select(id);
        return p;
    }

    @ApiOperation(value = "Update details of an order item")
    @RequestMapping(path = "/api/order/{id}",method = RequestMethod.PUT)
    public void update(@PathVariable int id,@RequestBody OrderItemForm form){
        OrderItemPojo p = convert(form);
        orderItemService.update(p);
    }

    public static OrderItemData convert(OrderItemPojo p){
        OrderItemData d = new OrderItemData();
        d.setId(p.getId());
        d.setQuantity(p.getQuantity());
        d.setOrderId(p.getOrderId());
        d.setProductId(p.getProductId());
        d.setSellingPrice(p.getSellingPrice());
        return d;
    }

    public static OrderItemPojo convert(OrderItemForm form){
        OrderItemPojo p = new OrderItemPojo();
        p.setId(form.getId());
        p.setQuantity(form.getQuantity());
        p.setOrderId(form.getOrderId());
        p.setProductId(form.getProductId());
        p.setSellingPrice(form.getSellingPrice());
        return p;
    }

}
