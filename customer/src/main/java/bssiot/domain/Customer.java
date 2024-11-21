package bssiot.domain;

import bssiot.CustomerApplication;
import bssiot.domain.OrderCreated;
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
    }

    public static CustomerRepository repository() {
        CustomerRepository customerRepository = CustomerApplication.applicationContext.getBean(
            CustomerRepository.class
        );
        return customerRepository;
    }

    public static void orderCancel(OrderCancelCommand orderCancelCommand) {
        
        System.out.println("check point2");
        
        repository().findById(orderCancelCommand.getId()).ifPresent(customer->{
                customer.setSvcContStatus("해지");
                repository().save(customer);

                OrderCancel orderCancel = new OrderCancel(customer);
                orderCancel.publishAfterCommit();
         });

    }

    public static void cancelAgain(RaterUnused raterUnused){
    
        repository().findById(raterUnused.getId()).ifPresent(customer ->{
            
            customer.setSvcContStatus("이미 해지됨");
            repository().save(customer);
        });
        
    }

       

    //>>> Clean Arch / Port Method
}
//>>> DDD / Aggregate Root
