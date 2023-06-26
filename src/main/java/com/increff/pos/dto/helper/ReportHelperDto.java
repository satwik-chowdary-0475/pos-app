package com.increff.pos.dto.helper;

import com.increff.pos.model.data.DailySalesData;
import com.increff.pos.model.data.InventoryReportData;
import com.increff.pos.pojo.DailySalesReportPojo;
import com.increff.pos.pojo.InventoryPojo;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportHelperDto {
    public static DailySalesData convertPojoToData(DailySalesReportPojo dailySalesReportPojo) {
        DailySalesData dailySalesData = new DailySalesData();
        dailySalesData.setTotal_revenue(dailySalesReportPojo.getTotal_revenue());
        dailySalesData.setInvoiced_items_count(dailySalesReportPojo.getInvoiced_items_count());
        dailySalesData.setInvoiced_orders_count(dailySalesReportPojo.getInvoiced_orders_count());
        dailySalesData.setDate(dailySalesReportPojo.getTime());
        return dailySalesData;
    }

    public static List<InventoryReportData> convertPojoToData(Map<Pair<String,String>,Integer>brandCategoryMap){
        List<InventoryReportData>inventoryReportDataList = new ArrayList<InventoryReportData>();
        for(Map.Entry<Pair<String,String>, Integer> entry : brandCategoryMap.entrySet()){
            String brand = entry.getKey().getKey();
            String category = entry.getKey().getValue();
            Integer quantity = entry.getValue();
            InventoryReportData inventoryReportData = new InventoryReportData();
            inventoryReportData.setBrand(brand);
            inventoryReportData.setCategory(category);
            inventoryReportData.setQuantity(quantity);
            inventoryReportDataList.add(inventoryReportData);
        }
        return inventoryReportDataList;
    }
}
