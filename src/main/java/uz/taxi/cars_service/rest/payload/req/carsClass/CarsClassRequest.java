package uz.taxi.cars_service.rest.payload.req.carsClass;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CarsClassRequest {

    @NotNull(message = "Type of service ID is required")
    private UUID typeOfServiceId;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    private Double startPrice;

    private Double kmPrice;

    private Double startKm;

    private Double startKmPrice;

    private Double eveningPrice;

    private Double paidWaitingTime;
}
