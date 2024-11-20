package bssiot.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "MyPage_table")
@Data
public class MyPage {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String customerId;
    private String productCd;
    private String svccontStatus;
    private Integer svcContNo;
    private String email;
    private String address;
    private String productNm;
}
