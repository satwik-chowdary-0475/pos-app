package com.increff.pos.controller;


import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@Api
@RestController
public class BrandApiController {
    @Autowired
    private BrandDto dto;
    @ApiOperation(value = "Inserts a brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        dto.insert(form);
    }

    @ApiOperation(value = "Updates a brand details")
    @RequestMapping(path = "/api/brand/{id}",method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm form) throws ApiException{
        dto.update(id,form);
    }

    @ApiOperation(value = "Get list of all brands")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAllBrand() throws ApiException {
        return dto.getAllBrand();
    }

    @ApiOperation(value = "Get a brand details")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandData getBrand(@PathVariable int id) throws ApiException {
        return dto.getBrand(id);
    }



}
