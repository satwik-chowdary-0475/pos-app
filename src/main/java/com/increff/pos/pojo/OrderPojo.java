package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Setter
@Getter
@Entity
@Table(
        name="orders"
)
public class OrderPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String status;
    private ZonedDateTime time;
}
