package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductData extends ProductForm{
    private int id;
    private String barcode;
}
