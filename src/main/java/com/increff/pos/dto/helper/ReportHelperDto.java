package com.increff.pos.dto.helper;

import com.increff.pos.model.data.DailySalesData;
import com.increff.pos.pojo.DailySalesReportPojo;

public class ReportHelperDto {
    public static DailySalesData convertPojoToData(DailySalesReportPojo dailySalesReportPojo) {
        DailySalesData dailySalesData = new DailySalesData();
        dailySalesData.setTotal_revenue(dailySalesReportPojo.getTotal_revenue());
        dailySalesData.setInvoiced_items_count(dailySalesReportPojo.getInvoiced_items_count());
        dailySalesData.setInvoiced_orders_count(dailySalesReportPojo.getInvoiced_orders_count());
        dailySalesData.setDate(dailySalesReportPojo.getTime());
        return dailySalesData;
    }
}
