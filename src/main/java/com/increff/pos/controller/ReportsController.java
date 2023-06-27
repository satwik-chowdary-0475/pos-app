package com.increff.pos.controller;

import com.increff.pos.dto.BrandDto;
import com.increff.pos.dto.ReportDto;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.data.DailySalesData;
import com.increff.pos.model.data.InventoryReportData;
import com.increff.pos.model.data.SalesData;
import com.increff.pos.model.form.SalesForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class ReportsController {

    @Autowired
    private BrandDto brandDto;

    @Autowired
    private ReportDto reportDto;

    @ApiOperation(value = "Get daily sales reports")
    @RequestMapping(path = "/api/report/day-on-day", method = RequestMethod.GET)
    public List<DailySalesData> getDailySalesReport() throws ApiException {
        return reportDto.getDailySalesReport();
    }

    @ApiOperation(value = "Get sales reports")
    @RequestMapping(path = "/api/report/sales", method = RequestMethod.POST)
    public List<SalesData> getSalesReport(@RequestBody SalesForm salesForm) throws ApiException {
        return reportDto.getSalesReport(salesForm);
    }



    @ApiOperation(value = "Get brand reports")
    @RequestMapping(path = "/api/report/brand-report",method = RequestMethod.GET)
    public List<BrandData> getBrandReport() throws ApiException{
        return reportDto.getBrandCategoryReport();
    }

    @ApiOperation(value = "Get inventory reports")
    @RequestMapping(path = "/api/report/inventory-report",method = RequestMethod.GET)
    public List<InventoryReportData> getInventoryReport() throws ApiException{
        return reportDto.getInventoryReport();
    }

}
