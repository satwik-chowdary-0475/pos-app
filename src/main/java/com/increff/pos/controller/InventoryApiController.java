package com.increff.pos.controller;


import com.increff.pos.dto.InventoryDto;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class InventoryApiController {

    @Autowired
    private InventoryDto dto;
    @Autowired
    private InventoryService inventoryService;
    @ApiOperation(value = "Inserts product to inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void add(@RequestBody InventoryForm form) throws ApiException {
        dto.insert(form);
//        inventoryService.insert(form.getId(), form.getQuantity());
    }

    @ApiOperation(value = "Deletes a product from inventory")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException {
        dto.delete(id);
    }

    @ApiOperation(value = "Get list of all products in inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAllProducts() {
        return dto.getAllProducts();
    }

    @ApiOperation(value = "Get a product from inventory")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData getProduct(@PathVariable int id) throws ApiException {
        return dto.getProduct(id);
    }



}
