package com.increff.pos.dto.util;

import com.increff.pos.dto.helper.HelperDto;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;

public class InventoryDtoUtil {

    public static InventoryData convert(InventoryPojo p){
        InventoryData d = new InventoryData();
        d.setId(p.getId());
        d.setQuantity(p.getQuantity());
        return d;
    }
    public static InventoryPojo convert(InventoryForm form){
        InventoryPojo p = new InventoryPojo();
        p.setQuantity(form.getQuantity());
        p.setId(form.getId());
        return p;
    }

    public static void formValidate(InventoryForm form) throws ApiException{
        if(form == null || (HelperDto.getNullPropertyNames(form).length > 0)){
            throw new ApiException("Invalid Inventory form ");
        }
    }
    public static void validate(ProductPojo p) throws ApiException{
        if(p == null){
            throw new ApiException("Product with given id doesn't exists!!!");
        }
    }
}
