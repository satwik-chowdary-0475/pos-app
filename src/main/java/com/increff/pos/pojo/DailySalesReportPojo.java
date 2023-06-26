package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "daily_sales"
)
public class DailySalesReportPojo {
    @Id
    private ZonedDateTime time;
    @Column(nullable = false)
    private Integer invoiced_orders_count;
    @Column(nullable = false)
    private Integer invoiced_items_count;
    @Column(nullable = false)
    private Double total_revenue;
}
