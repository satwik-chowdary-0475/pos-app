package com.increff.pos.dto;

import com.increff.pos.dto.helper.HelperDto;
import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDto {

    @Autowired
    private OrderService orderService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private OrderItemService orderItemService;
    public void insert(OrderForm form) throws ApiException {
        OrderPojo orderPojo = HelperDto.convertFormToOrder(form);
        HelperDto.validate(orderPojo);
        HelperDto.normalise(orderPojo);
        orderService.insert(orderPojo);
    }

    public List<OrderData>getAllOrders() throws ApiException{
        List<OrderData>dataList = new ArrayList<OrderData>();
        List<OrderPojo>list = orderService.selectAll();
        for(OrderPojo p:list){
            dataList.add(HelperDto.convertFormToOrder(p));
        }
        return dataList;
    }

    public void delete(int id) throws ApiException{
        orderService.delete(id);
        orderItemService.deleteByOrder(id);
    }

    public void invoice(int id) throws ApiException{
        OrderPojo orderPojo = orderService.select(id);
        if(orderPojo.getStatus() == "ACTIVE"){
            orderService.invoice(id);
            List<OrderItemPojo> orderItemsList = orderItemService.selectAll(id);
            inventoryService.reduceInventory(orderItemsList);
        }
    }

    public OrderData getOrder(int id) throws ApiException{
        OrderPojo orderPojo = orderService.select(id);
        return HelperDto.convertFormToOrder(orderPojo);
    }
}
