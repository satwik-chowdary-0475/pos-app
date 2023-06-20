package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Setter
@Getter
public class ProductForm {
    private int brandCategory;
    private String name;
    private double mrp;
 }
