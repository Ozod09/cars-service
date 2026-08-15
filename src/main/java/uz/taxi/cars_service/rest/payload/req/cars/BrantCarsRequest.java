package uz.taxi.cars_service.rest.payload.req.cars;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrantCarsRequest {
    @NotNull(message = "not id")
    private UUID carsId;
    @NotEmpty(message = "not photo")
    private List<String> brentCarsTakePhotoPaths;
}
