package com.increff.pos.controller;


import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api
@RestController
public class BrandApiController {
    @Autowired
    private BrandDto brandDto;
    @ApiOperation(value = "Inserts a brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        brandDto.insert(form);
    }

    @ApiOperation(value = "Updates a brand details")
    @RequestMapping(path = "/api/brand/{id}",method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm form) throws ApiException{
        brandDto.update(id,form);
    }

    @ApiOperation(value = "Get list of all brands")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAllBrand() throws ApiException {
        return brandDto.getAllBrand();
    }

    @ApiOperation(value = "Get a brand details")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandData getBrand(@PathVariable int id) throws ApiException {
        return brandDto.getBrand(id);
    }



}
