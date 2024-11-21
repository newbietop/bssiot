package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class RaterUnused extends AbstractEvent {

    private Long id;
    private Integer svcContNo;
    private String apnCd;
    private Long useAmount;
    private String useYn;
}
