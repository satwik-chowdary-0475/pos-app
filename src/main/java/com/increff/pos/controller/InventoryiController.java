package com.increff.pos.controller;


import com.increff.pos.dto.InventoryDto;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class InventoryiController {

    @Autowired
    private InventoryDto inventoryDto;
    @ApiOperation(value = "Inserts product to inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void add(@RequestBody InventoryForm form) throws ApiException {
        inventoryDto.insert(form);
    }

    @ApiOperation(value = "Updates a product in inventory")
    @RequestMapping(path = "/api/inventory/{id}",method = RequestMethod.PUT)
    public void update(@PathVariable int id,@RequestBody InventoryForm form) throws ApiException{
        inventoryDto.update(id,form);
    }

    @ApiOperation(value = "Deletes a product from inventory")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException {
        inventoryDto.delete(id);
    }

    @ApiOperation(value = "Get list of all products in inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAllProducts() throws ApiException {
        return inventoryDto.getAllProducts();
    }

    @ApiOperation(value = "Get a product from inventory")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData getProduct(@PathVariable int id) throws ApiException {
        return inventoryDto.getProduct(id);
    }



}
