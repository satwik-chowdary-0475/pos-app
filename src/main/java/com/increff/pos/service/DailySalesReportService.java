package com.increff.pos.service;

import com.increff.pos.dao.DailySalesReportDao;
import com.increff.pos.dao.OrderDao;
import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.DailySalesReportPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Log4j
public class DailySalesReportService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private DailySalesReportDao dailySalesReportDao;

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void insert(){
        Double totalRevenue = 0.0;
        Integer totalInvoicedItems = 0;
        Date currentDay = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDay);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date previousDay = (Date) calendar.getTime();
        List<OrderPojo> orderPojoList = orderDao.selectByDate(currentDay,previousDay);
        for(OrderPojo orderPojo:orderPojoList){
            Object[] orderItemReport = orderItemDao.selectByDate(orderPojo.getId());
            Double revenue = (Double) orderItemReport[1];
            Long invoicedItems = (Long) orderItemReport[0];
            totalInvoicedItems += invoicedItems.intValue();
            totalRevenue += revenue.doubleValue();
        }
        DailySalesReportPojo dailySalesReportPojo = setDailySalesReport(totalInvoicedItems,totalRevenue,orderPojoList.size());
        dailySalesReportDao.insert(dailySalesReportPojo);
    }

    public DailySalesReportPojo setDailySalesReport(Integer totalInvoicedItems,Double totalRevenue, int ordersCount){
        DailySalesReportPojo dailySalesReportPojo = new DailySalesReportPojo();
        dailySalesReportPojo.setInvoicedItemsCount((Integer) totalInvoicedItems);
        dailySalesReportPojo.setInvoicedOrdersCount(ordersCount);
        dailySalesReportPojo.setTotalRevenue(totalRevenue);
        dailySalesReportPojo.setTime(ZonedDateTime.now());
        return dailySalesReportPojo;
    }

    public List<DailySalesReportPojo> selectAll() {
        return dailySalesReportDao.selectAll();
    }
}
