package bssiot.domain;

import bssiot.CustomerApplication;
import bssiot.domain.OrderChaged;
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

    @PostPersist
    public void onPostPersist() {
        OrderCreated orderCreated = new OrderCreated(this);
        orderCreated.publishAfterCommit();

        OrderChaged orderChaged = new OrderChaged(this);
        orderChaged.publishAfterCommit();
    }

    public static CustomerRepository repository() {
        CustomerRepository customerRepository = CustomerApplication.applicationContext.getBean(
            CustomerRepository.class
        );
        return customerRepository;
    }

    //<<< Clean Arch / Port Method
    public static void chageProd(Prodchanged prodchanged) {
        //implement business logic here:

        /** Example 1:  new item 
        Customer customer = new Customer();
        repository().save(customer);

        */

        /** Example 2:  finding and process
        
        repository().findById(prodchanged.get???()).ifPresent(customer->{
            
            customer // do something
            repository().save(customer);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
