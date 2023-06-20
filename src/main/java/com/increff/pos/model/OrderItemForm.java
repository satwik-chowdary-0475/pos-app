package com.increff.pos.model;

public class OrderItemForm{
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double sellingPrice;

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getProductId() {
        return productId;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
