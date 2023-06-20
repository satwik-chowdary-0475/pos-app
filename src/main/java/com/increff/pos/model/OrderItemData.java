package com.increff.pos.model;

public class OrderItemData extends OrderItemForm{

    private int id;

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

}
