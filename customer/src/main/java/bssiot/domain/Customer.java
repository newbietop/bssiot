package bssiot.domain;

import bssiot.CustomerApplication;
import bssiot.domain.OrderCancel;
import bssiot.domain.OrderCreated;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Customer_table")
@Data
//<<< DDD / Aggregate Root
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerId;

    private String productCd;

    private String svcContStatus;

    private Integer svcContNo;

    private String email;

    private String address;

    private String productNm;

    private Integer productTarif;

    private Integer chargeAccount;

    private String apnCd;

    @PostPersist
    public void onPostPersist() {
        OrderCreated orderCreated = new OrderCreated(this);
        orderCreated.publishAfterCommit();

        OrderCancel orderCancle = new OrderCancel(this);
        orderCancle.publishAfterCommit();
    }

    public static CustomerRepository repository() {
        CustomerRepository customerRepository = CustomerApplication.applicationContext.getBean(
            CustomerRepository.class
        );
        return customerRepository;
    }

    public static void raterUnuse(RaterUnused raterUnused) {
        //implement business logic here:

        /** Example 1:  new item 
        Customer customer = new Customer();
        repository().save(customer);

        */

        /** Example 2:  finding and process
        
        repository().findById(raterUnused.get???()).ifPresent(customer->{
            
            customer // do something
            repository().save(customer);


         });
        */

    }
    //>>> Clean Arch / Port Method
}
//>>> DDD / Aggregate Root
