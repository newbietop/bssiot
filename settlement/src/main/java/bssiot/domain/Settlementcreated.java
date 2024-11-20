package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Settlementcreated extends AbstractEvent {

    private Long id;

    public Settlementcreated(Settlement aggregate) {
        super(aggregate);
    }

    public Settlementcreated() {
        super();
    }
}
//>>> DDD / Domain Event
