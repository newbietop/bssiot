package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class InvoiceCreated extends AbstractEvent {

    private Long id;
    private Integer chageAccount;
    private String productCd;
    private Integer productTarif;
    private String invoiceFileNm;
    private String invoiceFilePath;
    private String productNm;
    private String chargeAmount;
    private String useAmount;

    public InvoiceCreated(Billing aggregate) {
        super(aggregate);
    }

    public InvoiceCreated() {
        super();
    }
}
//>>> DDD / Domain Event
