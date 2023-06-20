package com.increff.pos.dto;

import com.increff.pos.dto.util.BrandDtoUtil;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandDto {
    @Autowired
    private BrandService service;

    public void insert(BrandForm form) throws ApiException {
        BrandDtoUtil.formValidate(form);
        BrandPojo p = BrandDtoUtil.convert(form);
        BrandDtoUtil.normalise(p);
        BrandDtoUtil.validate(p);
        service.insert(p);
    }

    public void update(int id,BrandForm form) throws ApiException{
        BrandDtoUtil.formValidate(form);
        BrandPojo p = BrandDtoUtil.convert(form);
        BrandDtoUtil.normalise(p);
        BrandDtoUtil.validate(p);
        service.update(id,p);
    }

    public List<BrandData> getAllBrand() throws ApiException{
        List<BrandPojo> list = service.selectAll();
        List<BrandData> dataList = new ArrayList<BrandData>();
        for(BrandPojo p : list){
            dataList.add(BrandDtoUtil.convert(p));
        }
        return dataList;
    }

    public BrandData getBrand(int id) throws ApiException{
        BrandPojo p = service.select(id);
        return BrandDtoUtil.convert(p);
    }


}
