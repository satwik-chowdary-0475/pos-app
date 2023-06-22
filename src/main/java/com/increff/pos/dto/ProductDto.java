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

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    public void insert(ProductForm form) throws ApiException {
        ProductPojo p = HelperDto.convertFormToProduct(form);
        HelperDto.validate(p);
        HelperDto.normalise(p);
        BrandPojo brandPojo = brandService.select(p.getBrandCategory());

        /* TODO: HANDLE SAME BARCODE CASE

        ProductPojo productPojo = productService.select(p.getBarcode());
        if(productPojo.getBarcode()!=null){
           throw new ApiException("Product with same barcode exist!!");
        }
*/
        productService.insert(p);
    }

    public void update(int id,ProductForm form) throws ApiException{
        ProductPojo p = HelperDto.convertFormToProduct(form);
        HelperDto.validate(p);
        HelperDto.normalise(p);
        BrandPojo brandPojo = brandService.select(p.getBrandCategory());
        ProductPojo productPojo = productService.select(p.getBarcode());
        if(productPojo.getBarcode()!=null){
            throw new ApiException("Product with same barcode exists!!");
        }
        productService.update(id,p);
    }

    public ProductData getProduct(int id) throws ApiException{
        ProductPojo p = productService.select(id);
        return HelperDto.convertFormToProduct(p);
    }

    public List<ProductData> getAllProducts() throws ApiException{
        List<ProductPojo> list = productService.selectAll();
        List<ProductData> dataList = new ArrayList<ProductData>();
        for(ProductPojo p : list){
            dataList.add(HelperDto.convertFormToProduct(p));
        }
        return dataList;
    }



}
