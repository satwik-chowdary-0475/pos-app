package com.increff.pos.dto;

import com.increff.pos.dto.util.InventoryDtoUtil;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryDto {

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService service;
    public void insert(InventoryForm form) throws ApiException {

        InventoryPojo p = InventoryDtoUtil.convert(form);
        ProductPojo productPojo = productService.select(p.getId());
        InventoryDtoUtil.validate(productPojo);
        service.insert(p);
    }
    public void delete(int id) throws ApiException{
        service.delete(id);
    }
    public InventoryData getProduct(int id) throws ApiException{
        return InventoryDtoUtil.convert(service.select(id));
    }
    public List<InventoryData> getAllProducts(){
        List<InventoryPojo> list = service.selectAll();
        List<InventoryData> dataList = new ArrayList<InventoryData>();
        for(InventoryPojo p: list){
            dataList.add(InventoryDtoUtil.convert(p));
        }
        return dataList;
    }

}
