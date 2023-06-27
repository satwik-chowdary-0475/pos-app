package com.increff.pos.dto;

import com.increff.pos.dto.helper.HelperDto;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemDto {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private InventoryService inventoryService;
    @Transactional(rollbackOn = ApiException.class)
    public void insert(int orderId,OrderItemForm orderItemForm) throws ApiException {
        ProductPojo productPojo = productService.select(orderItemForm.getBarcode());
        HelperDto.normalise(orderItemForm);
        OrderItemPojo orderItemPojo = HelperDto.convert(orderItemForm,orderId,productPojo);
        InventoryPojo inventoryPojo = inventoryService.select(orderItemPojo.getProductId());
        orderItemService.insert(orderItemPojo,inventoryPojo);
        int requiredQuantity = orderItemPojo.getQuantity();
        int inventoryQuantity = inventoryPojo.getQuantity();
        inventoryService.update(inventoryPojo,inventoryQuantity-requiredQuantity);
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<OrderItemData> getOrderItems(int orderId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList = orderItemService.selectAll(orderId);
        List<OrderItemData>orderItemDataList = new ArrayList<OrderItemData>();
        for(OrderItemPojo orderItemPojo: orderItemPojoList){
            ProductPojo productPojo = productService.select(orderItemPojo.getProductId());
            orderItemDataList.add(HelperDto.convert(orderItemPojo,productPojo.getBarcode(),productPojo.getName()));
        }
        return orderItemDataList;
    }


    @Transactional(rollbackOn = ApiException.class)
    public OrderItemData getOrderItem(int orderId, int id) throws ApiException {
        OrderItemPojo orderItemPojo = orderItemService.select(orderId,id);
        ProductPojo productPojo = productService.select(orderItemPojo.getProductId());
        return HelperDto.convert(orderItemPojo,productPojo.getBarcode(),productPojo.getName());
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int orderId,int id,OrderItemForm orderItemForm) throws ApiException{
        ProductPojo productPojo = productService.select(orderItemForm.getBarcode());
        HelperDto.normalise(orderItemForm);
        OrderItemPojo orderItemPojo = HelperDto.convert(orderItemForm,orderId,productPojo);
        InventoryPojo inventoryPojo = inventoryService.select(productPojo.getId());
        OrderItemPojo existingOrderItemPojo = orderItemService.select(orderId,id);
        int requiredQuantity = orderItemPojo.getQuantity();
        int inventoryQuantity = inventoryPojo.getQuantity();
        int previousQuantity = existingOrderItemPojo.getQuantity();
        orderItemService.update(orderId,id,orderItemPojo,inventoryPojo);
        int updatedQuantity = inventoryQuantity + previousQuantity - requiredQuantity;
        inventoryService.update(inventoryPojo, updatedQuantity);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete(int orderId,int id) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemService.select(orderId,id);
        InventoryPojo inventoryPojo = inventoryService.select(orderItemPojo.getProductId());
        orderItemService.delete(orderId,id);
        inventoryService.update(inventoryPojo,inventoryPojo.getQuantity() + orderItemPojo.getQuantity());
    }

}
