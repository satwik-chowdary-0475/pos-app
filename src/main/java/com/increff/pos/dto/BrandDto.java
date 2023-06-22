package com.increff.pos.dto;

import com.increff.pos.dto.helper.HelperDto;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.form.BrandForm;
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
    private BrandService brandService;

    public void insert(BrandForm form) throws ApiException {
        BrandPojo p = HelperDto.convertFormToBrand(form);
        HelperDto.validate(p);
        HelperDto.normalise(p);
        brandService.insert(p);
    }

    public void update(int id,BrandForm form) throws ApiException{
        BrandPojo p = HelperDto.convertFormToBrand(form);
        HelperDto.validate(p);
        HelperDto.normalise(p);
        brandService.update(id,p);
    }

    public List<BrandData> getAllBrand() throws ApiException{
        List<BrandPojo> list = brandService.selectAll();
        List<BrandData> dataList = new ArrayList<BrandData>();
        for(BrandPojo p : list){
            dataList.add(HelperDto.convertFormToBrand(p));
        }
        return dataList;
    }

    public BrandData getBrand(int id) throws ApiException{
        BrandPojo p = brandService.select(id);
        return HelperDto.convertFormToBrand(p);
    }



}
