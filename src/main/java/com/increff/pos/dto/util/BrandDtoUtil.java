package com.increff.pos.dto.util;

import com.increff.pos.dto.helper.HelperDto;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.util.StringUtil;
public class BrandDtoUtil {

    public static void formValidate(BrandForm form) throws ApiException{
        if(form == null || (HelperDto.getNullPropertyNames(form).length > 0)){
            throw new ApiException("Invalid brand form ");
        }
    }
    public static void validate(BrandPojo p) throws ApiException {
        if((p.getBrand().length() == 0) || (p.getCategory().length() == 0) ) throw new ApiException("invalid brand details!!!");
    }
    public static void normalise(BrandPojo p){
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
    }
    public static BrandData convert(BrandPojo p){
        BrandData d = new BrandData();
        d.setId(p.getId());
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        return d;
    }
    public static BrandPojo convert(BrandForm form){
        BrandPojo p = new BrandPojo();
        p.setBrand(form.getBrand());
        p.setCategory(form.getCategory());
        return p;
    }

}
