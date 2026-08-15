package uz.taxi.cars_service.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaReqUser implements Serializable {

    private String userId;
    private String fullname;
    private String phone;
    private int roleName;
}
