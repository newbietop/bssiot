package bssiot.domain;

import bssiot.RateApplication;
import bssiot.domain.Aumoved;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Rater_table")
@Data
//<<< DDD / Aggregate Root
public class Rater {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer svcContNo;

    private String apnCd;

    private Long useAmount;

    @PostPersist
    public void onPostPersist() {
        Aumoved aumoved = new Aumoved(this);
        aumoved.publishAfterCommit();
    }

    public static RaterRepository repository() {
        RaterRepository raterRepository = RateApplication.applicationContext.getBean(
            RaterRepository.class
        );
        return raterRepository;
    }

    //<<< Clean Arch / Port Method
    public static void createRaterInfo(OrderCreated orderCreated) {
        //implement business logic here:

        /** Example 1:  new item 
        Rater rater = new Rater();
        repository().save(rater);

        */

        /** Example 2:  finding and process
        
        repository().findById(orderCreated.get???()).ifPresent(rater->{
            
            rater // do something
            repository().save(rater);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root