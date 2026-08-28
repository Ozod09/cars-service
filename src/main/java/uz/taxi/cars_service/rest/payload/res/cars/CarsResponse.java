package uz.taxi.cars_service.rest.payload.res.cars;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CarsResponse {

    private UUID id;

    private UUID driverId;

    private String firstName;

    private String lastName;

    private String phone;

    private String attachmentPath;

    private String model;

    private String number;

    private String texPassportPhotoPath;

    private List<UUID> carsServiceIds;

    private String brentStatus;

    private List<String> brentCarsTakePhotoPaths;

    private String status;

    private Long carRedisId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
