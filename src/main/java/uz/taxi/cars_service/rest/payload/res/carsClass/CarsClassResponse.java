package uz.taxi.cars_service.rest.payload.res.carsclass;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CarsClassResponse {

    private UUID id;

    private UUID typeOfServiceId;

    private String typeOfServiceName;

    private String name;

    private String description;

    private Double startPrice;

    private Double oneKmPrice;

    private Double daytimePrice;

    private Double eveningPrice;

    private Double paidWaitingTime;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
