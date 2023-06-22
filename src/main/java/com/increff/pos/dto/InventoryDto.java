package com.increff.pos.dto;

import com.increff.pos.dto.helper.HelperDto;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.form.InventoryForm;
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
    private InventoryService inventoryService;

    public void insert(InventoryForm form) throws ApiException {
        ProductPojo productPojo = productService.select(form.getBarcode());
        InventoryPojo inventoryPojo = HelperDto.convertFormToInventory(form,productPojo.getId());
        HelperDto.validate(inventoryPojo);
        inventoryService.insert(inventoryPojo);
    }

    public void update(int id,InventoryForm form) throws ApiException{
        ProductPojo productPojo = productService.select(id);
        InventoryPojo inventoryPojo = HelperDto.convertFormToInventory(form,productPojo.getId());
        HelperDto.validate(inventoryPojo);
        inventoryService.update(inventoryPojo);
    }
    public void delete(int id) throws ApiException{
        inventoryService.delete(id);
    }
    public InventoryData getProduct(int id) throws ApiException{
        InventoryPojo p = inventoryService.select(id);
        ProductPojo productPojo = productService.select(p.getId());
        return HelperDto.convertFormToInventory(p,productPojo.getBarcode());
    }
    public List<InventoryData> getAllProducts() throws ApiException {
        List<InventoryPojo> list = inventoryService.selectAll();
        List<InventoryData> dataList = new ArrayList<InventoryData>();
        for(InventoryPojo p: list){
            ProductPojo productPojo = productService.select(p.getId());
            dataList.add(HelperDto.convertFormToInventory(p,productPojo.getBarcode()));
        }
        return dataList;
    }

}
