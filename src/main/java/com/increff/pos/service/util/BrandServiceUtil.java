package com.increff.pos.service.util;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;

public class BrandServiceUtil {

    public static void validate(BrandPojo brandPojo) throws ApiException{
        if(brandPojo == null) throw new ApiException("brand item doesn't exist!!");
    }

}
