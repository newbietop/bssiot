package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Prodchanged extends AbstractEvent {

    private Long id;
    private String productCd;
    private String productNm;

    public Prodchanged(Billing aggregate) {
        super(aggregate);
    }

    public Prodchanged() {
        super();
    }
}
//>>> DDD / Domain Event
