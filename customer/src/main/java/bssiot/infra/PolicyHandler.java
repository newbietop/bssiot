package bssiot.infra;

import bssiot.config.kafka.KafkaProcessor;
import bssiot.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    CustomerRepository customerRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Prodchanged'"
    )
    public void wheneverProdchanged_ChageProd(
        @Payload Prodchanged prodchanged
    ) {
        Prodchanged event = prodchanged;
        System.out.println(
            "\n\n##### listener ChageProd : " + prodchanged + "\n\n"
        );

        // Sample Logic //
        Customer.chageProd(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
