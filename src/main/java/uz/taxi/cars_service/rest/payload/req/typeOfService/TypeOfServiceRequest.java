package uz.taxi.cars_service.rest.payload.req.typeOfService;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TypeOfServiceRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    private UUID regionId;

    private String attachmentPath;
}
