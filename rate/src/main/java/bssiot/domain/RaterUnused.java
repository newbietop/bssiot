package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class RaterUnused extends AbstractEvent {

    private Long id;
    private Integer svcContNo;
    private String apnCd;
    private Long useAmount;
    private String useYn;

    public RaterUnused(Rater aggregate) {
        super(aggregate);
    }

    public RaterUnused() {
        super();
    }
}
//>>> DDD / Domain Event
