package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class OrderCreated extends AbstractEvent {

    private Long id;
    private String customerId;
    private String productCd;
    private Integer svcContNo;
    private String email;
    private String address;
    private String productNm;
    private String svcContStatus;
    private Integer productTarif;
    private Integer chargeAccount;

    public OrderCreated(Customer aggregate) {
        super(aggregate);
    }

    public OrderCreated() {
        super();
    }
}
//>>> DDD / Domain Event
