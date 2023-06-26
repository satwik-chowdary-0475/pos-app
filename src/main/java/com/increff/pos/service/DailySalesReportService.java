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
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
        List<OrderPojo> orderPojoList = orderDao.selectByDate();
        for(OrderPojo orderPojo:orderPojoList){
            Object[] orderItemReport = orderItemDao.selectByDate(orderPojo.getId());
            Double revenue = (Double) orderItemReport[1];
            Long invoicedItems = (Long) orderItemReport[0];
            totalInvoicedItems += invoicedItems.intValue();
            totalRevenue += revenue.floatValue();
        }
        DailySalesReportPojo dailySalesReportPojo = new DailySalesReportPojo();
        dailySalesReportPojo.setInvoiced_items_count((Integer) totalInvoicedItems);
        dailySalesReportPojo.setInvoiced_orders_count(orderPojoList.size());
        dailySalesReportPojo.setTotal_revenue(totalRevenue);
        dailySalesReportPojo.setTime(ZonedDateTime.now());
        dailySalesReportDao.insert(dailySalesReportPojo);
    }


    public List<DailySalesReportPojo> selectAll() {
        return dailySalesReportDao.selectAll();
    }
}
