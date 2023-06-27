package com.increff.pos.dto;

import com.increff.pos.dto.helper.HelperDto;
import com.increff.pos.dto.helper.ReportHelperDto;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.data.InventoryReportData;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryDto {
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BrandService brandService;

    @Transactional(rollbackOn = ApiException.class)
    public void insert(InventoryForm inventoryForm) throws ApiException {
        ProductPojo productPojo = productService.select(inventoryForm.getBarcode());
        HelperDto.validate(inventoryForm);
        InventoryPojo inventoryPojo = HelperDto.convert(inventoryForm, productPojo.getId());
        inventoryService.insert(inventoryPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, InventoryForm inventoryForm) throws ApiException {
        ProductPojo productPojo = productService.select(id);
        HelperDto.validate(inventoryForm);
        InventoryPojo inventoryPojo = HelperDto.convert(inventoryForm, productPojo.getId());
        inventoryService.update(inventoryPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete(int id) throws ApiException {
        inventoryService.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryData getProduct(int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.select(id);
        ProductPojo productPojo = productService.select(inventoryPojo.getId());
        return HelperDto.convert(inventoryPojo, productPojo.getBarcode(),productPojo.getName());
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<InventoryData> getAllProducts() throws ApiException {
        List<InventoryPojo> inventoryPojoList = inventoryService.selectAll();
        List<InventoryData> inventoryDataList = new ArrayList<InventoryData>();
        for (InventoryPojo inventoryPojo : inventoryPojoList) {
            ProductPojo productPojo = productService.select(inventoryPojo.getId());
            inventoryDataList.add(HelperDto.convert(inventoryPojo, productPojo.getBarcode(),productPojo.getName()));
        }
        return inventoryDataList;
    }


}
