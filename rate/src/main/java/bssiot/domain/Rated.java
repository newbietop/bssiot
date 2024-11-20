package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Rated extends AbstractEvent {

    private Long id;
    private Integer svcContNo;
    private String apnCd;
    private Long useAmount;

    public Rated(Rater aggregate) {
        super(aggregate);
    }

    public Rated() {
        super();
    }
}
//>>> DDD / Domain Event