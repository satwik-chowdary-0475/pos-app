package com.increff.pos.dto;

import com.increff.pos.dto.helper.ReportHelperDto;
import com.increff.pos.model.data.DailySalesData;
import com.increff.pos.pojo.DailySalesReportPojo;
import com.increff.pos.service.DailySalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailySalesReportDto {

    @Autowired
    private DailySalesReportService dailySalesReportService;

    public List<DailySalesData> selectAll(){
        List<DailySalesReportPojo> dailySalesReportPojoList =  dailySalesReportService.selectAll();
        List<DailySalesData> dailySalesData = new ArrayList<DailySalesData>();
        for(DailySalesReportPojo dailySalesReportPojo : dailySalesReportPojoList){
            dailySalesData.add(ReportHelperDto.convertPojoToData(dailySalesReportPojo));
        }
        return dailySalesData;
    }

}
