package phoneseller;

import phoneseller.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    GiftRepository giftRepository;


    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCompleted_PointSave(@Payload PayCompleted payCompleted){

        // 비동기 방식으로 카프카 리스너를 통해 결제가 완료된 이벤트를 파악 -> 프로모션 포인트 제공
        if(payCompleted.isMe()){
            System.out.println("###### wheneverPayCompleted_PointSave");

            Gift gift = new Gift();
            gift.setOrderId(payCompleted.getOrderId());
            if(payCompleted.getPrice() != null && payCompleted.getPrice() > 0) {
                gift.setPoint(payCompleted.getPrice() * 0.5);
                gift.setProcess("##### Payed");
            }
            giftRepository.save(gift);

            System.out.println("##### wheneverPayCompleted_PointSave End : " + payCompleted.toJson());
        }
    }

}
