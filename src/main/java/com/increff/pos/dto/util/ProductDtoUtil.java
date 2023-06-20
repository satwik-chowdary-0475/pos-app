package com.increff.pos.dto.util;

import com.increff.pos.dto.helper.HelperDto;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.util.RandomStrGenerator;
import com.increff.pos.util.StringUtil;

public class ProductDtoUtil {

    public static ProductData convert(ProductPojo p){
        ProductData d = new ProductData();
        d.setId(p.getId());
        d.setBrandCategory(p.getBrandCategory());
        d.setBarcode(p.getBarcode());
        d.setMrp(p.getMrp());
        d.setName(p.getName());
        return d;
    }
    public static ProductPojo convert(ProductForm form){
        ProductPojo p = new ProductPojo();
        p.setBarcode(RandomStrGenerator.usingRandomUUID());
        p.setMrp(form.getMrp());
        p.setName(form.getName());
        p.setBrandCategory(form.getBrandCategory());
        return p;
    }

    public static void normalise(ProductPojo p){
        p.setName(StringUtil.toLowerCase(p.getName()));
    }

    public static void validateBrand(BrandPojo brandPojo) throws ApiException{
        if(brandPojo == null){
            throw new ApiException("Cannot add product as brand doesn't exist!!");
        }
    }

    public static void formValidate(ProductForm form) throws ApiException{
        if(form == null || (HelperDto.getNullPropertyNames(form).length > 0)){

            throw new ApiException("Invalid product form ");
        }
    }
    public static void validateProduct(ProductPojo p) throws ApiException{
        if(p == null){
            throw new ApiException("Product doesn't exist!!");
        }
    }

}
