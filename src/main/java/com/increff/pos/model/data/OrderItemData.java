package com.increff.pos.model.data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemData{
    private Integer id;
    private Integer orderId;
    private String productName;
    private Integer quantity;
    private Float sellingPrice;
    private String barcode;
}
