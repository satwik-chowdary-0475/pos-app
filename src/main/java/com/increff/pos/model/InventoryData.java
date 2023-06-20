package com.increff.pos.model;
public class InventoryData extends InventoryForm{
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
