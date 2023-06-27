package com.increff.pos.dto;

import com.increff.pos.dto.helper.HelperDto;
import com.increff.pos.dto.helper.ReportHelperDto;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.data.DailySalesData;
import com.increff.pos.model.data.InventoryReportData;
import com.increff.pos.model.data.SalesData;
import com.increff.pos.model.form.SalesForm;
import com.increff.pos.pojo.*;
import com.increff.pos.service.*;
import com.increff.pos.util.StringUtil;
import javafx.util.Pair;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@Log4j
public class ReportDto {

    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private DailySalesReportService dailySalesReportService;
    @Autowired
    private OrderService orderService;
    @Transactional(rollbackOn = ApiException.class)
    public List<InventoryReportData> getInventoryReport() throws ApiException {
        List<BrandPojo> brandPojoList = brandService.selectAll();
        List<InventoryReportData>inventoryReportDataList = new ArrayList<InventoryReportData>();
        for(BrandPojo brandPojo : brandPojoList){
            InventoryReportData inventoryReportData = new InventoryReportData();
            inventoryReportData.setBrand(brandPojo.getBrand());
            inventoryReportData.setCategory(brandPojo.getCategory());
            inventoryReportData.setQuantity(getInventoryQuantity(brandPojo));
            inventoryReportDataList.add(inventoryReportData);
        }
        return inventoryReportDataList;
    }

    @Transactional(rollbackOn = ApiException.class)
    public int getInventoryQuantity(BrandPojo brandPojo){
        List<ProductPojo>productPojoList = new ArrayList<ProductPojo>();
        productPojoList.addAll(productService.selectByBrandId(brandPojo.getId()));
        int totalQuantity = 0;
        for(ProductPojo productPojo : productPojoList){
            try{
                InventoryPojo inventoryPojo = inventoryService.select(productPojo.getId());
                totalQuantity += inventoryPojo.getQuantity();
            }
            catch (ApiException e){
                ;
            }
        }
        return totalQuantity;
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<DailySalesData> getDailySalesReport(){
        List<DailySalesReportPojo> dailySalesReportPojoList =  dailySalesReportService.selectAll();
        List<DailySalesData> dailySalesData = new ArrayList<DailySalesData>();
        for(DailySalesReportPojo dailySalesReportPojo : dailySalesReportPojoList){
            dailySalesData.add(ReportHelperDto.convert(dailySalesReportPojo));
        }
        return dailySalesData;
    }

    @Transactional
    public List<BrandData> getBrandCategoryReport(){
        List<BrandPojo> list = brandService.selectAll();
        List<BrandData> dataList = new ArrayList<BrandData>();
        for(BrandPojo p : list){
            dataList.add(HelperDto.convert(p));
        }
        return dataList;
    }


    @Transactional(rollbackOn = ApiException.class)
    public List<SalesData> getSalesReport(SalesForm salesForm) throws ApiException {
        ReportHelperDto.normalise(salesForm);
        Date startTime = Date.valueOf(salesForm.getStartTime());
        Date endTime = Date.valueOf(salesForm.getEndTime());
        List<SalesData> salesDataList = new ArrayList<SalesData>();
        List<BrandPojo>brandPojoList = brandService.selectByBrandCategory(salesForm.getBrand(), salesForm.getCategory());
        for(BrandPojo brandPojo : brandPojoList){
            List<ProductPojo>productPojoList = productService.selectByBrandId(brandPojo.getId());
            List<OrderPojo>orderPojoList = orderService.selectByDate(startTime,endTime);
            List<OrderItemPojo> orderItemPojoList = getOrderItemsList(orderPojoList,productPojoList);
            Integer quantity = new Integer(0);
            Double totalRevenue = new Double(0.0);
            for(OrderItemPojo orderItemPojo : orderItemPojoList){
                quantity = quantity + orderItemPojo.getQuantity();
                totalRevenue = totalRevenue + (orderItemPojo.getSellingPrice() * orderItemPojo.getQuantity());
            }
            salesDataList.add(ReportHelperDto.convert(brandPojo,quantity,totalRevenue));
        }
        return salesDataList;

    }


    // TODO : OPTIMISE THIS IN A BETTER WAY
    public List<OrderItemPojo>getOrderItemsList(List<OrderPojo>orderPojoList,List<ProductPojo>productPojoList) {
        List<OrderItemPojo>orderItemPojoList = new ArrayList<OrderItemPojo>();
        for(OrderPojo orderPojo : orderPojoList){
            for(ProductPojo productPojo : productPojoList){
                OrderItemPojo orderItemPojo = orderItemService.selectByProduct(orderPojo.getId(),productPojo.getId());
                if(!Objects.isNull(orderItemPojo)) orderItemPojoList.add(orderItemPojo);
            }
        }
        return orderItemPojoList;
    }

}
