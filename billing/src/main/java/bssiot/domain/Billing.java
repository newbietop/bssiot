package bssiot.domain;

import bssiot.BillingApplication;
import bssiot.domain.Chargecalculated;
import bssiot.domain.InvoiceCreated;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Billing_table")
@Data
//<<< DDD / Aggregate Root
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer chargeAccount;

    private String productCd;

    private Integer productTarif;

    private String invoiceFileNm;

    private String invoiceFilePath;

    private String productNm;

    private Long chargeAmount;

    private Long useAmount;

    private Integer svcContNo;

    @PostPersist
    public void onPostPersist() {
        Chargecalculated chargecalculated = new Chargecalculated(this);
        chargecalculated.publishAfterCommit();

        InvoiceCreated invoiceCreated = new InvoiceCreated(this);
        invoiceCreated.publishAfterCommit();
    }

    public static BillingRepository repository() {
        BillingRepository billingRepository = BillingApplication.applicationContext.getBean(
            BillingRepository.class
        );
        return billingRepository;
    }

    //<<< Clean Arch / Port Method
    public static void createChargeInfo(OrderCreated orderCreated) {
        Billing billing = new Billing();

        billing.setProductCd(orderCreated.getProductCd());
        billing.setProductNm(orderCreated.getProductNm());
        billing.setChargeAccount(orderCreated.getChargeAccount());
        billing.setProductTarif(orderCreated.getProductTarif());
        billing.setSvcContNo(orderCreated.getSvcContNo());

        repository().save(billing);
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void chargeCalculating(Aumoved aumoved) {
        //implement business logic here:

        /** Example 1:  new item 
        Billing billing = new Billing();
        repository().save(billing);

        Chargecalculated chargecalculated = new Chargecalculated(billing);
        chargecalculated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(aumoved.get???()).ifPresent(billing->{
            
            billing // do something
            repository().save(billing);

            Chargecalculated chargecalculated = new Chargecalculated(billing);
            chargecalculated.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void createInvoice(Chargecalculated chargecalculated) {
        //implement business logic here:

        /** Example 1:  new item 
        Billing billing = new Billing();
        repository().save(billing);

        InvoiceCreated invoiceCreated = new InvoiceCreated(billing);
        invoiceCreated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(chargecalculated.get???()).ifPresent(billing->{
            
            billing // do something
            repository().save(billing);

            InvoiceCreated invoiceCreated = new InvoiceCreated(billing);
            invoiceCreated.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
