package uz.taxi.cars_service.kafka;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KafkaReqAddAuthUser implements Serializable {
    
    private String uid;
    private String name;
    private String phone;
    private String password;
    private int roleName;
}
