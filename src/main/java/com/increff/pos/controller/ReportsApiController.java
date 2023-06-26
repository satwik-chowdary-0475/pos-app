package com.increff.pos.controller;

import com.increff.pos.dao.DailySalesReportDao;
import com.increff.pos.dto.BrandDto;
import com.increff.pos.dto.DailySalesReportDto;
import com.increff.pos.dto.InventoryDto;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.data.DailySalesData;
import com.increff.pos.model.data.InventoryReportData;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.DailySalesReportService;
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
public class ReportsApiController {

    @Autowired
    private DailySalesReportDto dailySalesReportDto;
    @Autowired
    private BrandDto brandDto;

    @Autowired
    private InventoryDto inventoryDto;

    @ApiOperation(value = "Get daily sales reports")
    @RequestMapping(path = "/api/report/day-on-day", method = RequestMethod.GET)
    public List<DailySalesData> getSalesReport() throws ApiException {
        return dailySalesReportDto.selectAll();
    }

    @ApiOperation(value = "Get brand reports")
    @RequestMapping(path = "/api/report/brand-report",method = RequestMethod.GET)
    public List<BrandData> getBrandReport() throws ApiException{
        return brandDto.getAllBrand();
    }

    @ApiOperation(value = "Get inventory reports")
    @RequestMapping(path = "/api/report/inventory-report",method = RequestMethod.GET)
    public List<InventoryReportData> getInventoryReport() throws ApiException{
        return inventoryDto.getReport();
    }

}
