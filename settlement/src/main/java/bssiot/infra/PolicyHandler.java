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
    SettlementRepository settlementRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='InvoiceCreated'"
    )
    public void wheneverInvoiceCreated_CreateSettleInfo(
        @Payload InvoiceCreated invoiceCreated
    ) {
        InvoiceCreated event = invoiceCreated;
        System.out.println(
            "\n\n##### listener CreateSettleInfo : " + invoiceCreated + "\n\n"
        );

        // Sample Logic //
        Settlement.createSettleInfo(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
