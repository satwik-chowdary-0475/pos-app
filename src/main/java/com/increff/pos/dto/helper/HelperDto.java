package com.increff.pos.dto.helper;

import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.model.data.*;
import com.increff.pos.model.form.*;
import com.increff.pos.pojo.*;
import com.increff.pos.service.ApiException;
import com.increff.pos.util.StringUtil;

import java.time.ZonedDateTime;


public class HelperDto {
    public static void validate(ProductPojo p) throws ApiException {
        if(p == null || p.getName()==null || p.getName().length()==0|| p.getMrp() == null || p.getBarcode() == null || (p.getBarcode().length() == 0) || p.getBrandCategory() == null){
            throw new ApiException("Invalid input form product details!!!");
        }
    }

    public static void validate(InventoryPojo p) throws ApiException {
        if(p == null || p.getId() == null || p.getQuantity() == null){
            throw new ApiException("Invalid input inventory form details!!");
        }
    }

    public static void validate(BrandPojo p) throws ApiException {
        if(p == null || p.getBrand()==null || p.getCategory() == null || (p.getBrand().length() == 0) || (p.getCategory().length() == 0) ) throw new ApiException("Invalid input form brand details!!!");
    }

    public static void validate(OrderPojo p) throws ApiException{
        if(p == null || p.getCustomerName() == null || p.getCustomerName().length() == 0){
            throw new ApiException("Invalid input order name!!!");
        }
    }

    public static void validate(OrderItemPojo p) throws ApiException{
        if(p == null || p.getProductId() == null || p.getQuantity() == null){
            throw new ApiException("Invalid input order item quantity!!");
        }
    }

    public static ProductData convertFormToProduct(ProductPojo p){
        ProductData d = new ProductData();
        d.setId(p.getId());
        d.setBrandCategory(p.getBrandCategory());
        d.setBarcode(p.getBarcode());
        d.setMrp(p.getMrp());
        d.setName(p.getName());
        return d;
    }
    public static ProductPojo convertFormToProduct(ProductForm form){
        ProductPojo p = new ProductPojo();
        p.setBarcode(form.getBarcode());
        p.setMrp(form.getMrp());
        p.setName(form.getName());
        p.setBrandCategory(form.getBrandCategory());
        return p;
    }

    public static InventoryData convertFormToInventory(InventoryPojo p, String barcode) throws ApiException{
        InventoryData d = new InventoryData();
        d.setBarcode(barcode);
        d.setId(p.getId());
        d.setQuantity(p.getQuantity());
        return d;
    }
    public static InventoryPojo convertFormToInventory(InventoryForm form,int id){
        InventoryPojo p = new InventoryPojo();
        p.setQuantity(form.getQuantity());
        p.setId(id);
        return p;
    }

    public static BrandData convertFormToBrand(BrandPojo p){
        BrandData d = new BrandData();
        d.setId(p.getId());
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        return d;
    }
    public static BrandPojo convertFormToBrand(BrandForm form){
        BrandPojo p = new BrandPojo();
        p.setBrand(form.getBrand());
        p.setCategory(form.getCategory());
        return p;
    }

    public static OrderPojo convertFormToOrder(OrderForm form){
        OrderPojo p = new OrderPojo();
        p.setCustomerName(form.getCustomerName());
        p.setStatus("ACTIVE");
        return p;
    }

    public static OrderData convertFormToOrder(OrderPojo p){
        OrderData orderData = new OrderData();
        orderData.setId(p.getId());
        orderData.setStatus(p.getStatus());
        orderData.setCustomerName(p.getCustomerName());
        return orderData;
    }

    public static OrderItemPojo convertFormToOrderItem(OrderItemForm form,int orderId,ProductPojo productPojo){
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productPojo.getId());
        orderItemPojo.setSellingPrice(productPojo.getMrp());
        orderItemPojo.setQuantity(form.getQuantity());
        return orderItemPojo;
    }

    public static OrderItemData convertFormToOrderItem(OrderItemPojo p,String barcode){
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setId(p.getId());
        orderItemData.setOrderId(p.getOrderId());
        orderItemData.setProductId(p.getProductId());
        orderItemData.setQuantity(p.getQuantity());
        orderItemData.setSellingPrice(p.getSellingPrice());
        orderItemData.setBarcode(barcode);
        return orderItemData;
    }

    public static void normalise(ProductPojo p){
        p.setName(StringUtil.toLowerCase(p.getName()));
    }

    public static void normalise(BrandPojo p){
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
    }

    public static void normalise(OrderPojo p){
        p.setCustomerName(StringUtil.toLowerCase(p.getCustomerName()));
    }

}
