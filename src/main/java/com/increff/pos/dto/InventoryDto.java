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
    public void insert(InventoryForm form) throws ApiException {
        ProductPojo productPojo = productService.select(form.getBarcode());
        InventoryPojo inventoryPojo = HelperDto.convert(form, productPojo.getId());
        HelperDto.validate(inventoryPojo,false);
        inventoryService.insert(inventoryPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, InventoryForm form) throws ApiException {
        ProductPojo productPojo = productService.select(id);
        InventoryPojo inventoryPojo = HelperDto.convert(form, productPojo.getId());
        HelperDto.validate(inventoryPojo,true);
        inventoryService.update(inventoryPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete(int id) throws ApiException {
        inventoryService.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryData getProduct(int id) throws ApiException {
        InventoryPojo p = inventoryService.select(id);
        ProductPojo productPojo = productService.select(p.getId());
        return HelperDto.convert(p, productPojo.getBarcode(),productPojo.getName());
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<InventoryData> getAllProducts() throws ApiException {
        List<InventoryPojo> list = inventoryService.selectAll();
        List<InventoryData> dataList = new ArrayList<InventoryData>();
        for (InventoryPojo p : list) {
            ProductPojo productPojo = productService.select(p.getId());
            dataList.add(HelperDto.convert(p, productPojo.getBarcode(),productPojo.getName()));
        }
        return dataList;
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<InventoryReportData> getReport() throws ApiException {
        List<InventoryPojo> inventoryPojoList = inventoryService.selectAll();
        Map<Pair<String, String>, Integer> brandCategoryMap = new HashMap<Pair<String, String>, Integer>();
        for (InventoryPojo inventoryPojo : inventoryPojoList) {
            ProductPojo productPojo = productService.select(inventoryPojo.getId());
            BrandPojo brandPojo = brandService.select(productPojo.getBrandCategory());
            Pair<String, String> brandCategoryPair = new Pair<>(brandPojo.getBrand(), brandPojo.getCategory());
            if (brandCategoryMap.containsKey(brandCategoryPair)) {
                int previousQuantity = Integer.valueOf(brandCategoryMap.get(brandCategoryPair));
                previousQuantity = previousQuantity + inventoryPojo.getQuantity();
                brandCategoryMap.put(brandCategoryPair, previousQuantity);
            } else {
                brandCategoryMap.put(brandCategoryPair, inventoryPojo.getQuantity());
            }
        }
        return ReportHelperDto.convertPojoToData(brandCategoryMap);
    }
}
