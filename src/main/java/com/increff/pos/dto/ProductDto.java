package com.increff.pos.dto;

import com.increff.pos.dto.util.ProductDtoUtil;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    public void insert(ProductForm form) throws ApiException {
        ProductDtoUtil.formValidate(form);
        ProductPojo p = ProductDtoUtil.convert(form);
        ProductDtoUtil.normalise(p);
        BrandPojo brandPojo = brandService.select(p.getBrandCategory());
        ProductDtoUtil.validateBrand(brandPojo);
        productService.insert(p);
    }

    public void update(int id,ProductForm form) throws ApiException{
        ProductDtoUtil.formValidate(form);
        ProductPojo p = ProductDtoUtil.convert(form);
        ProductDtoUtil.normalise(p);
        ProductDtoUtil.validateProduct(p);
        BrandPojo brandPojo = brandService.select(p.getBrandCategory());
        ProductDtoUtil.validateBrand(brandPojo);
        productService.update(id,p);
    }

    public ProductData getProduct(int id) throws ApiException{
        ProductPojo p = productService.select(id);
        ProductDtoUtil.validateProduct(p);
        return ProductDtoUtil.convert(p);
    }

    public List<ProductData> getAllProducts() throws ApiException{
        List<ProductPojo> list = productService.selectAll();
        List<ProductData> dataList = new ArrayList<ProductData>();
        for(ProductPojo p : list){
            dataList.add(ProductDtoUtil.convert(p));
        }
        return dataList;
    }

}
