package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class OrderChaged extends AbstractEvent {

    private Long id;
    private String customerId;
    private String productCd;
    private String svcContStatus;
    private Integer svcContNo;
    private String email;
    private String address;
    private String productNm;

    public OrderChaged(Customer aggregate) {
        super(aggregate);
    }

    public OrderChaged() {
        super();
    }
}
//>>> DDD / Domain Event
