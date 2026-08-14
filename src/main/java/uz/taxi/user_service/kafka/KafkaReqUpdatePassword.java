package uz.taxi.user_service.kafka;

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
