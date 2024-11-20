package bssiot.domain;

import bssiot.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class OrderChaged extends AbstractEvent {

    private Long id;
    private String customerId;
    private String productCd;
    private String svcContStatus;
    private Integer svcContNo;
    private String email;
    private String address;
    private String productNm;
}
