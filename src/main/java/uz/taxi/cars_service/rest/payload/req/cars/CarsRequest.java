package uz.taxi.cars_service.rest.payload.req.cars;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CarsRequest {

    private UUID id;

    @NotNull(message = "Driver ID is required")
    private UUID driverId;

    private String firstName;

    private String lastName;

    private String phone;

    private String attachmentPath;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Number is required")
    private String number;

    private String texPassportPhotoPath;

    private List<UUID> carsServiceIds;

}
