package uz.taxi.cars_service.kafka;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KafkaReqUpdatePassword implements Serializable {
    
    private String userId;
    private String password;
}
