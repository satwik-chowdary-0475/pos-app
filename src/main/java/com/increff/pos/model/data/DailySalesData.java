package com.increff.pos.model.data;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class DailySalesData {
    private ZonedDateTime date;
    private Integer invoiced_orders_count;
    private Integer invoiced_items_count;
    private Double total_revenue;
}
