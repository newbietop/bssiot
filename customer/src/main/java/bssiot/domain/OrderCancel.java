package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class OrderCancel extends AbstractEvent {

    private Long id;
    private String svcContStatus;
    private Integer svcContNo;
    private String apnCd;

    public OrderCancel(Customer aggregate) {
        super(aggregate);
    }

    public OrderCancel() {
        super();
    }
}
//>>> DDD / Domain Event