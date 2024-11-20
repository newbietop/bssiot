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
    RaterRepository raterRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderCreated'"
    )
    public void wheneverOrderCreated_CreateRaterInfo(
        @Payload OrderCreated orderCreated
    ) {
        OrderCreated event = orderCreated;
        System.out.println(
            "\n\n##### listener CreateRaterInfo : " + orderCreated + "\n\n"
        );

        // Sample Logic //
        Rater.createRaterInfo(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Rated'"
    )
    public void wheneverRated_AuAdd(@Payload Rated rated) {
        Rated event = rated;
        System.out.println("\n\n##### listener AuAdd : " + rated + "\n\n");

        // Sample Logic //
        Rater.auAdd(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
