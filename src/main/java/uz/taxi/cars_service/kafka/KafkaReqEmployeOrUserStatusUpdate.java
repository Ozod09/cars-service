package uz.taxi.cars_service.kafka;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KafkaReqEmployeOrUserStatusUpdate implements Serializable {
    
    private String id;
    private int status;
}
