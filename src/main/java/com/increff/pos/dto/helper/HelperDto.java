package com.increff.pos.dto.helper;

import com.increff.pos.model.data.*;
import com.increff.pos.model.form.*;
import com.increff.pos.pojo.*;
import com.increff.pos.service.ApiException;
import com.increff.pos.util.RoundUtil;
import com.increff.pos.util.StringUtil;


public class HelperDto {
    public static void validate(ProductPojo p) throws ApiException {
        if(p == null){
            throw new ApiException("Invalid input form product details!!!");
        }
        if(p.getName() == null || p.getName().length() == 0){
            throw new ApiException("Invalid product name");
        }
        if( p.getBarcode() == null || (p.getBarcode().length() == 0)){
            throw new ApiException("Invalid product barcode");
        }
        if(p.getMrp() == null || p.getMrp() > 10000000 || p.getMrp() <= 0){
            throw new ApiException("Invalid product Mrp");
        }
        if(p.getBrandCategory()==null){
            throw new ApiException("Invalid brand category!");
        }
        if(p.getName().length()>30){
            throw new ApiException("Product name cannot be more than 30 characters");
        }
        if(p.getBarcode().length() > 30){
            throw new ApiException("Barcode cannot be more than 30 characters");
        }

    }

    public static void validate(InventoryPojo p) throws ApiException {
        if(p == null){
            throw new ApiException("Invalid inventory form details");
        }
        if(p.getId() == null || p.getId() <= 0){
            throw new ApiException("Invalid product id!!");
        }
        if(p.getQuantity() == null || p.getQuantity() > 1000000 || p.getQuantity() <= 0){
            throw new ApiException("Invalid product quantity");
        }
    }

    public static void validate(BrandPojo p) throws ApiException {
        if(p == null){
            throw new ApiException("Invalid input form brand details!!!");
        }
        if(p.getBrand()==null || (p.getBrand().length() == 0)){
            throw new ApiException("Invalid brand name");
        }
        if(p.getCategory() == null || (p.getCategory().length() == 0)){
            throw new ApiException("Invalid category name");
        }

        if(p.getBrand().length()>30){
            throw new ApiException("Brand name cannot have more than 30 characters");
        }
        if(p.getCategory().length() > 30){
            throw new ApiException("Category name cannot have more than 30 characters");
        }

    }

    public static void validate(OrderPojo p) throws ApiException{
        if(p == null){
            throw new ApiException("Invalid order form details");
        }
        if( p.getCustomerName() == null || p.getCustomerName().length() == 0){
            throw new ApiException("Invalid order name!!!");
        }
        if(p.getCustomerName().length() > 30){
            throw new ApiException("Customer name cannot have more than 30 characters");
        }
    }

    public static void validate(OrderItemPojo p) throws ApiException{
        if(p == null){
            throw new ApiException("Invalid order item form!!");
        }
        if(p.getProductId() == null || p.getProductId() <=0){
            throw new ApiException("Invalid product id");
        }
        if( p.getQuantity()==null || p.getQuantity() > 1000000 || p.getQuantity() <= 0){
            throw new ApiException("Invalid product quantity");
        }
        if(p.getSellingPrice() == null || p.getSellingPrice() <= 0 || p.getSellingPrice()>1000000){
            throw new ApiException("Invalid selling price");
        }
    }

    public static ProductData convertFormToProduct(ProductPojo p,String brand,String category){
        ProductData d = new ProductData();
        d.setId(p.getId());
        d.setBrand(brand);
        d.setCategory(category);
        d.setBarcode(p.getBarcode());
        d.setMrp(p.getMrp());
        d.setName(p.getName());
        return d;
    }
    public static ProductPojo convertFormToProduct(ProductForm form,int brandId){
        ProductPojo p = new ProductPojo();
        p.setBarcode(form.getBarcode());
        p.setMrp(form.getMrp());
        p.setName(form.getName());
        p.setBrandCategory(brandId);
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
        orderData.setCreatedAt(p.getCreatedAt());
        return orderData;
    }

    public static OrderItemPojo convertFormToOrderItem(OrderItemForm form,int orderId,ProductPojo productPojo){
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productPojo.getId());
        orderItemPojo.setSellingPrice(form.getSellingPrice());
        orderItemPojo.setQuantity(form.getQuantity());
        return orderItemPojo;
    }

    public static OrderItemData convertFormToOrderItem(OrderItemPojo p,String barcode,String productName){
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setId(p.getId());
        orderItemData.setOrderId(p.getOrderId());
        orderItemData.setProductName(productName);
        orderItemData.setQuantity(p.getQuantity());
        orderItemData.setSellingPrice(p.getSellingPrice());
        orderItemData.setBarcode(barcode);
        return orderItemData;
    }

    public static void normalise(ProductPojo p){
        p.setName(StringUtil.toLowerCase(p.getName()));
    }

    public static void normalise(ProductForm form){
        form.setBrand(StringUtil.toLowerCase(form.getBrand()));
        form.setCategory(StringUtil.toLowerCase(form.getCategory()));
    }

    public static void normalise(BrandPojo p){
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
    }

    public static void normalise(OrderPojo p){
        p.setCustomerName(StringUtil.toLowerCase(p.getCustomerName()));
    }

    public static void roundFloat(OrderItemPojo p){
        p.setSellingPrice(RoundUtil.round(p.getSellingPrice()));
    }

    public static void roundFloat(ProductPojo p){
        p.setMrp(RoundUtil.round(p.getMrp()));
    }

}
