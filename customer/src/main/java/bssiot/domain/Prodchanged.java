package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class Prodchanged extends AbstractEvent {

    private Long id;
    private String productCd;
    private String productNm;
}
