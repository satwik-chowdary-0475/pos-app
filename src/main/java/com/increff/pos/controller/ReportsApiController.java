package com.increff.pos.controller;

import com.increff.pos.dao.DailySalesReportDao;
import com.increff.pos.dto.DailySalesReportDto;
import com.increff.pos.model.data.DailySalesData;
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
    DailySalesReportDto dailySalesReportDto;

    @ApiOperation(value = "Get daily sales reports")
    @RequestMapping(path = "/api/report/day-on-day", method = RequestMethod.GET)
    public List<DailySalesData> getSalesReport() throws ApiException {
        return dailySalesReportDto.selectAll();
    }
}
