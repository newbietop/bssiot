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
    BillingRepository billingRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderCreated'"
    )
    public void wheneverOrderCreated_CreateChargeInfo(
        @Payload OrderCreated orderCreated
    ) {
        OrderCreated event = orderCreated;
        System.out.println(
            "\n\n##### listener CreateChargeInfo : " + orderCreated + "\n\n"
        );

        // Sample Logic //
        Billing.createChargeInfo(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Aumoved'"
    )
    public void wheneverAumoved_ChargeCalculating(@Payload Aumoved aumoved) {
        Aumoved event = aumoved;
        System.out.println(
            "\n\n##### listener ChargeCalculating : " + aumoved + "\n\n"
        );

        // Sample Logic //
        Billing.chargeCalculating(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Chargecalculated'"
    )
    public void wheneverChargecalculated_CreateInvoice(
        @Payload Chargecalculated chargecalculated
    ) {
        Chargecalculated event = chargecalculated;
        System.out.println(
            "\n\n##### listener CreateInvoice : " + chargecalculated + "\n\n"
        );

        // Sample Logic //
        Billing.createInvoice(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
