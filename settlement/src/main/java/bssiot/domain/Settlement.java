package bssiot.domain;

import bssiot.SettlementApplication;
import bssiot.domain.Settlementcreated;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Settlement_table")
@Data
//<<< DDD / Aggregate Root
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long totalUseAmount;

    private Long totalChargAmount;

    private String settleFileNm;

    private String settleFilePath;

    @PostPersist
    public void onPostPersist() {
        Settlementcreated settlementcreated = new Settlementcreated(this);
        settlementcreated.publishAfterCommit();
    }

    public static SettlementRepository repository() {
        SettlementRepository settlementRepository = SettlementApplication.applicationContext.getBean(
            SettlementRepository.class
        );
        return settlementRepository;
    }

    //<<< Clean Arch / Port Method
    public static void createSettleInfo(InvoiceCreated invoiceCreated) {
        //implement business logic here:

        /** Example 1:  new item 
        Settlement settlement = new Settlement();
        repository().save(settlement);

        Settlementcreated settlementcreated = new Settlementcreated(settlement);
        settlementcreated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(invoiceCreated.get???()).ifPresent(settlement->{
            
            settlement // do something
            repository().save(settlement);

            Settlementcreated settlementcreated = new Settlementcreated(settlement);
            settlementcreated.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
