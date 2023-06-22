package com.increff.pos.dto;

import com.increff.pos.dto.helper.HelperDto;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemDto {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;

    public void insert(int orderId,OrderItemForm form) throws ApiException {
        ProductPojo productPojo = productService.select(form.getBarcode());
        OrderItemPojo orderItemPojo = HelperDto.convertFormToOrderItem(form,orderId,productPojo);
        HelperDto.validate(orderItemPojo);
        orderItemService.insert(orderItemPojo);
    }

    public List<OrderItemData> getOrderItems(int orderId) throws ApiException {
        List<OrderItemPojo> list = orderItemService.selectAll(orderId);
        List<OrderItemData>dataList = new ArrayList<OrderItemData>();
        for(OrderItemPojo p: list){
            ProductPojo productPojo = productService.select(p.getProductId());
            dataList.add(HelperDto.convertFormToOrderItem(p,productPojo.getBarcode()));
        }
        return dataList;
    }


    public OrderItemData getOrderItem(int orderId, int id) throws ApiException {
        OrderItemPojo orderItemPojo = orderItemService.select(orderId,id);
        ProductPojo productPojo = productService.select(orderItemPojo.getProductId());
        return HelperDto.convertFormToOrderItem(orderItemPojo,productPojo.getBarcode());
    }

    public void update(int orderId,int id,OrderItemForm form) throws ApiException{

        ProductPojo productPojo = productService.select(form.getBarcode());
        OrderItemPojo orderItemPojo = HelperDto.convertFormToOrderItem(form,orderId,productPojo);
        HelperDto.validate(orderItemPojo);
        orderItemService.update(orderId,id,orderItemPojo);
    }

    public void delete(int orderId,int id) throws ApiException{
        orderItemService.delete(orderId,id);
    }

}
