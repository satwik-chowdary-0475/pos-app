package com.increff.pos.dto.helper;

import com.increff.pos.model.data.*;
import com.increff.pos.model.form.*;
import com.increff.pos.pojo.*;
import com.increff.pos.service.ApiException;
import com.increff.pos.util.RoundUtil;
import com.increff.pos.util.StringUtil;

public class HelperDto {
    public static void validate(ProductForm productForm) throws ApiException{
        if(productForm == null){
            throw new ApiException("Invalid input form product details!!!");
        }
        if(productForm.getName() == null || productForm.getName().length() == 0){
            throw new ApiException("Invalid product name");
        }
        if( productForm.getBarcode() == null || (productForm.getBarcode().length() == 0)){
            throw new ApiException("Invalid product barcode");
        }
        if(productForm.getBrand() == null || productForm.getBrand().length() == 0){
            throw new ApiException("Invalid brand name!!");
        }
        if(productForm.getCategory() == null || productForm.getCategory().length() == 0){
            throw new ApiException("Invalid brand category!!");
        }
        if(productForm.getMrp() == null || productForm.getMrp() > 10000000 || productForm.getMrp() <= 0){
            throw new ApiException("Invalid product Mrp");
        }
        if(productForm.getName().length()>30){
            throw new ApiException("Product name cannot be more than 30 characters");
        }
        if(productForm.getBarcode().length() > 30){
            throw new ApiException("Barcode cannot be more than 30 characters");
        }
        if(productForm.getBrand().length()>30){
            throw new ApiException("Brand cannot be more than 30 characters");
        }
        if(productForm.getCategory().length() > 30){
            throw new ApiException("Category cannot be more than 30 characters");
        }
    }

    public static void validate(InventoryForm inventoryForm) throws ApiException {
        if(inventoryForm == null){
            throw new ApiException("Invalid inventory form details");
        }
        if(inventoryForm.getBarcode() == null || inventoryForm.getBarcode().length() == 0){
            throw new ApiException("Invalid product barcode!!");
        }
        if(inventoryForm.getQuantity() == null || inventoryForm.getQuantity() > 1000000 || inventoryForm.getQuantity() < 0 ){
            throw new ApiException("Invalid product quantity");
        }
    }


    public static void validate(BrandForm brandForm) throws ApiException{
        if(brandForm == null){
            throw new ApiException("Invalid input form brand details!!!");
        }
        if(brandForm.getBrand()==null || (brandForm.getBrand().length() == 0)){
            throw new ApiException("Invalid brand name");
        }
        if(brandForm.getCategory() == null || (brandForm.getCategory().length() == 0)){
            throw new ApiException("Invalid category name");
        }

        if(brandForm.getBrand().length()>30){
            throw new ApiException("Brand name cannot have more than 30 characters");
        }
        if(brandForm.getCategory().length() > 30){
            throw new ApiException("Category name cannot have more than 30 characters");
        }
    }

    public static void validate(OrderForm orderForm) throws ApiException{
        if(orderForm == null){
            throw new ApiException("Invalid order form details");
        }
        if( orderForm.getCustomerName() == null || orderForm.getCustomerName().length() == 0){
            throw new ApiException("Invalid order name!!!");
        }
        if(orderForm.getCustomerName().length() > 30){
            throw new ApiException("Customer name cannot have more than 30 characters");
        }
    }

    public static void validate(OrderItemForm orderItemForm) throws ApiException{
        if(orderItemForm == null){
            throw new ApiException("Invalid order item form!!");
        }
        if(orderItemForm.getBarcode() == null || orderItemForm.getBarcode().length()<=0){
            throw new ApiException("Invalid barcode id");
        }
        if( orderItemForm.getQuantity()==null || orderItemForm.getQuantity() > 1000000 || orderItemForm.getQuantity() <= 0){
            throw new ApiException("Invalid product quantity");
        }
        if(orderItemForm.getSellingPrice() == null || orderItemForm.getSellingPrice() <= 0 || orderItemForm.getSellingPrice()>1000000){
            throw new ApiException("Invalid selling price");
        }
    }

    public static ProductData convert(ProductPojo p,String brand,String category){
        ProductData d = new ProductData();
        d.setId(p.getId());
        d.setBrand(brand);
        d.setCategory(category);
        d.setBarcode(p.getBarcode());
        d.setMrp(p.getMrp());
        d.setName(p.getName());
        return d;
    }
    public static ProductPojo convert(ProductForm form,int brandId){
        ProductPojo p = new ProductPojo();
        p.setBarcode(form.getBarcode());
        p.setMrp(form.getMrp());
        p.setName(form.getName());
        p.setBrandCategory(brandId);
        return p;
    }

    public static InventoryData convert(InventoryPojo p, String barcode,String productName) throws ApiException{
        InventoryData d = new InventoryData();
        d.setProductName(productName);
        d.setBarcode(barcode);
        d.setId(p.getId());
        d.setQuantity(p.getQuantity());
        return d;
    }
    public static InventoryPojo convert(InventoryForm form,int id){
        InventoryPojo p = new InventoryPojo();
        p.setQuantity(form.getQuantity());
        p.setId(id);
        return p;
    }

    public static BrandData convert(BrandPojo p){
        BrandData d = new BrandData();
        d.setId(p.getId());
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        return d;
    }
    public static BrandPojo convert(BrandForm form){
        BrandPojo p = new BrandPojo();
        p.setBrand(form.getBrand());
        p.setCategory(form.getCategory());
        return p;
    }

    public static OrderPojo convert(OrderForm form){
        OrderPojo p = new OrderPojo();
        p.setCustomerName(form.getCustomerName());
        p.setStatus("ACTIVE");
        return p;
    }

    public static OrderData convert(OrderPojo p){
        OrderData orderData = new OrderData();
        orderData.setId(p.getId());
        orderData.setStatus(p.getStatus());
        orderData.setCustomerName(p.getCustomerName());
        orderData.setCreatedAt(p.getCreatedAt());
        return orderData;
    }

    public static OrderItemPojo convert(OrderItemForm form,int orderId,ProductPojo productPojo){
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productPojo.getId());
        orderItemPojo.setSellingPrice(form.getSellingPrice());
        orderItemPojo.setQuantity(form.getQuantity());
        return orderItemPojo;
    }

    public static OrderItemData convert(OrderItemPojo p,String barcode,String productName){
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setId(p.getId());
        orderItemData.setOrderId(p.getOrderId());
        orderItemData.setProductName(productName);
        orderItemData.setQuantity(p.getQuantity());
        orderItemData.setSellingPrice(p.getSellingPrice());
        orderItemData.setBarcode(barcode);
        return orderItemData;
    }

    public static void normalise(ProductForm productForm) throws ApiException {
        productForm.setBrand(StringUtil.toLowerCase(productForm.getBrand()));
        productForm.setCategory(StringUtil.toLowerCase(productForm.getCategory()));
        productForm.setMrp(RoundUtil.round(productForm.getMrp()));
        productForm.setName(StringUtil.toLowerCase(productForm.getName()));
        productForm.setBrand(StringUtil.toLowerCase(productForm.getBrand()));
        productForm.setCategory(StringUtil.toLowerCase(productForm.getCategory()));
        HelperDto.validate(productForm);
    }


    public static void normalise(BrandForm brandForm) throws ApiException {
        brandForm.setBrand(StringUtil.toLowerCase(brandForm.getBrand()));
        brandForm.setCategory(StringUtil.toLowerCase(brandForm.getCategory()));
        HelperDto.validate(brandForm);
    }

    public static void normalise(OrderForm orderForm) throws ApiException{
        orderForm.setCustomerName(StringUtil.toLowerCase(orderForm.getCustomerName()));
        HelperDto.validate(orderForm);
    }

    public static void normalise(OrderItemForm orderItemForm) throws ApiException{
        orderItemForm.setSellingPrice(RoundUtil.round(orderItemForm.getSellingPrice()));
        HelperDto.validate(orderItemForm);
    }

}
