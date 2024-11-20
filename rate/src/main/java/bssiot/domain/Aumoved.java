package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Aumoved extends AbstractEvent {

    private Long id;
    private Integer svcContNo;
    private String apnCd;
    private Long useAmount;

    public Aumoved(Rater aggregate) {
        super(aggregate);
    }

    public Aumoved() {
        super();
    }
}
//>>> DDD / Domain Event
