package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.util.*;
import lombok.*;

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
}
