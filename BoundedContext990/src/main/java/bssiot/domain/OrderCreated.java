package bssiot.domain;

import bssiot.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class OrderCreated extends AbstractEvent {

    private Long id;
    private String customerId;
    private String productCd;
    private Integer svcContNo;
    private String email;
    private String address;
    private String productNm;
    private String svcContStatus;
}
