package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractPojo {
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    @Version
    private int version;
}
