package com.increff.pos.dto;

import com.increff.pos.dto.helper.HelperDto;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;

    @Transactional(rollbackOn = ApiException.class)
    public void insert(ProductForm form) throws ApiException {
        HelperDto.normalise(form);
        BrandPojo brandPojo = brandService.select(form.getBrand(),form.getCategory());
        ProductPojo p = HelperDto.convert(form,brandPojo.getId());
        HelperDto.normalise(p);
        HelperDto.validate(p);
        /* TODO: HANDLE SAME BARCODE CASE

        ProductPojo productPojo = productService.select(p.getBarcode());
        if(productPojo.getBarcode()!=null){
           throw new ApiException("Product with same barcode exist!!");
        }
*/
        productService.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id,ProductForm form) throws ApiException{
        HelperDto.normalise(form);
        BrandPojo brandPojo = brandService.select(form.getBrand(),form.getCategory());
        ProductPojo p = HelperDto.convert(form, brandPojo.getId());
        HelperDto.normalise(p);
        HelperDto.validate(p);
        productService.update(id,p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductData getProduct(int id) throws ApiException{
        ProductPojo productPojo = productService.select(id);
        BrandPojo brandPojo = brandService.select(productPojo.getBrandCategory());
        return HelperDto.convert(productPojo,brandPojo.getBrand(),brandPojo.getCategory());
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<ProductData> getAllProducts() throws ApiException{
        List<ProductPojo> list = productService.selectAll();
        List<ProductData> dataList = new ArrayList<ProductData>();
        for(ProductPojo p : list){
            BrandPojo brandPojo = brandService.select(p.getBrandCategory());
            dataList.add(HelperDto.convert(p,brandPojo.getBrand(), brandPojo.getCategory()));
        }
        return dataList;
    }



}
