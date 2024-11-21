package bssiot.infra;

import bssiot.domain.*;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/customers")
@Transactional
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping(
        value = "/customers/{id}/ordercancel",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Customer orderCancel(
        @PathVariable(value = "id") Long id,
        @RequestBody OrderCancelCommand orderCancelCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /customer/orderCancel  called #####");
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        optionalCustomer.orElseThrow(() -> new Exception("No Entity Found"));
        Customer customer = optionalCustomer.get();
        customer.orderCancel(orderCancelCommand);

        System.out.println("cheack"+customer);

        return customer;
    }
}
//>>> Clean Arch / Inbound Adaptor
