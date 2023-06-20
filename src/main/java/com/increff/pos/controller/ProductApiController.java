package com.increff.pos.controller;


import com.increff.pos.dto.ProductDto;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class ProductApiController {

    @Autowired
    private ProductDto dto;

    @ApiOperation(value = "Inserts a product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException {
        dto.insert(form);
    }

    @ApiOperation(value = "Update a product")
    @RequestMapping(path = "/api/product/{id}",method = RequestMethod.PUT)
    public void update(@PathVariable int id,@RequestBody ProductForm form) throws ApiException{
        dto.update(id,form);

    }
//    @ApiOperation(value = "Deletes a product")
//    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.DELETE)
//    public void delete(@PathVariable int id) throws ApiException {
//        service.delete(id);
//    }

    @ApiOperation(value = "Get list of all products")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductData> getAllProduct() throws ApiException {
        return dto.getAllProducts();
    }

    @ApiOperation(value = "Get a product details")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
    public ProductData getProduct(@PathVariable int id) throws ApiException {
        return dto.getProduct(id);
    }


}
