package bssiot.domain;

import bssiot.domain.*;
import bssiot.infra.AbstractEvent;
import java.util.*;
import lombok.*;

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
}
