package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class OrderCancel extends AbstractEvent {

    private Long id;
    private String svcContStatus;
    private Integer svcContNo;
    private String apnCd;
}