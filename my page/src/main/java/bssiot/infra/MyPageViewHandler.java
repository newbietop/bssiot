package bssiot.infra;

import bssiot.config.kafka.KafkaProcessor;
import bssiot.domain.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MyPageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MyPageRepository myPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCreated_then_CREATE_1(
        @Payload OrderCreated orderCreated
    ) {
        try {
            if (!orderCreated.validate()) return;

            // view 객체 생성
            MyPage myPage = new MyPage();
            // view 객체에 이벤트의 Value 를 set 함
            myPage.setCustomerId(orderCreated.getCustomerId());
            myPage.setProductCd(orderCreated.getProductCd());
            myPage.setSvcContNo(orderCreated.getSvcContNo());
            myPage.setEmail(orderCreated.getEmail());
            myPage.setAddress(orderCreated.getAddress());
            myPage.setProductNm(orderCreated.getProductNm());
            myPage.setSvccontStatus("변경됨");
            // view 레파지 토리에 save
            myPageRepository.save(myPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderChaged_then_UPDATE_1(
        @Payload OrderChaged orderChaged
    ) {
        try {
            if (!orderChaged.validate()) return;
            // view 객체 조회

            List<MyPage> myPageList = myPageRepository.findBySvcContNo(
                orderChaged.getSvcContNo()
            );
            for (MyPage myPage : myPageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setSvccontStatus("변경됨");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
