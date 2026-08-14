package uz.taxi.user_service.rest.payload.req.driver;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
public class DriverRequest {

    @NotNull(message = "Not driver id")
    private UUID id;

    @NotBlank(message = "First name null")
    private String firstName;

    @NotBlank(message = "Last name null")
    private String lastName;

    @NotNull(message = "Invalid is phone")
    @Pattern(
            regexp = "^\\+998\\d{9}$",
            message = "Phone number must be in format +998XXXXXXXXX"
    )
    private String phone;

//    private String attachmentPath;

    @NotNull(message = "Birth date null")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Passport serial number null")
    private String passportSerialNumber;

    private String passportPhotoPath;
}
