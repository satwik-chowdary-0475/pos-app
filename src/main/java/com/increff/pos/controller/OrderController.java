package com.increff.pos.controller;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderForm;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class OrderController {

    @Autowired
    private OrderDto orderDto;
    @Autowired
    private OrderItemDto orderItemDto;

    @ApiOperation(value = "Creates an order")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public void insert(@RequestBody OrderForm form) throws ApiException {
        orderDto.insert(form);
    }

    @ApiOperation(value = "Get details of an order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public OrderData getOrder(@PathVariable int id) throws ApiException {
        return orderDto.getOrder(id);
    }

    @ApiOperation(value = "Get list of all orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData> getAllOrders() throws ApiException {
        return orderDto.getAllOrders();
    }

    @ApiOperation(value = "Delete an order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException {
        orderDto.delete(id);
    }

    @ApiOperation(value = "Invoice an order!!")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.PUT)
    public void invoiceOrder(@PathVariable int id) throws ApiException {
        orderDto.invoice(id);
    }


    @ApiOperation(value = "Add order item to the order")
    @RequestMapping(path = "/api/order/{orderId}/order-items", method = RequestMethod.POST)
    public void insertOrderItem(@PathVariable int orderId, @RequestBody OrderItemForm form) throws ApiException {
        orderItemDto.insert(orderId, form);
    }

    @ApiOperation(value = "Get all order items of a order")
    @RequestMapping(path = "/api/order/{orderId}/order-items", method = RequestMethod.GET)
    public List<OrderItemData> getOrderItems(@PathVariable int orderId) throws ApiException {
        return orderItemDto.getOrderItems(orderId);
    }

    @ApiOperation(value = "Get an order-item")
    @RequestMapping(path = "/api/order/{orderId}/order-items/{id}", method = RequestMethod.GET)
    public OrderItemData getOrderItem(@PathVariable int orderId, @PathVariable int id) throws ApiException {
        return orderItemDto.getOrderItem(orderId, id);
    }

    @ApiOperation(value = "Update an order-item")
    @RequestMapping(path = "/api/order/{orderId}/order-items/{id}", method = RequestMethod.PUT)
    public void updateOrderItem(@PathVariable int orderId, @PathVariable int id, @RequestBody OrderItemForm form) throws ApiException {
        orderItemDto.update(orderId, id, form);
    }

    @ApiOperation(value = "Delete an order-item")
    @RequestMapping(path = "/api/order/{orderId}/order-items/{id}", method = RequestMethod.DELETE)
    public void deleteOrderItem(@PathVariable int orderId, @PathVariable int id) throws ApiException {
        orderItemDto.delete(orderId, id);
    }
}
