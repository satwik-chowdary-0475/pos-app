package com.increff.pos.dto;

import com.increff.pos.dto.helper.HelperDto;
import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;
import java.util.*;

@Log4j
@Service
public class OrderDto {

    @Autowired
    private OrderService orderService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private OrderItemService orderItemService;
    @Transactional(rollbackOn = ApiException.class)
    public void insert(OrderForm form) throws ApiException {
        OrderPojo orderPojo = HelperDto.convert(form);
        HelperDto.normalise(orderPojo);
        HelperDto.validate(orderPojo);
        orderService.insert(orderPojo);
    }

    @Transactional
    public List<OrderData>getAllOrders(){
        List<OrderData>dataList = new ArrayList<OrderData>();
        List<OrderPojo>list = orderService.selectAll();
        for(OrderPojo p:list){
            dataList.add(HelperDto.convert(p));
        }
        return dataList;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete(int id) throws ApiException{
        orderService.delete(id);
        Map<InventoryPojo,Integer> inventoryPojoDict= new Hashtable<>();
        List<OrderItemPojo>orderItemPojoList = orderItemService.selectAll(id);
        for(OrderItemPojo orderItemPojo : orderItemPojoList){
            inventoryPojoDict.put(inventoryService.select(orderItemPojo.getProductId()),orderItemPojo.getQuantity());
        }
        orderItemService.deleteByOrder(id,inventoryPojoDict);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void invoice(int id) throws ApiException{
        OrderPojo orderPojo = orderService.select(id);
        if(orderPojo.getStatus().equals("ACTIVE")){
            orderService.invoice(id);
        }
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderData getOrder(int id) throws ApiException{
        OrderPojo orderPojo = orderService.select(id);
        return HelperDto.convert(orderPojo);
    }
}
