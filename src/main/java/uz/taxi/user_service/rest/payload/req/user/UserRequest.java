package uz.taxi.user_service.rest.payload.req.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import uz.taxi.user_service.enums.RoleName;

import java.util.UUID;


@Getter
@Setter
public class UserRequest {

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

    private String attachmentPath;

    private String password;  // Optional for edit

    private UUID regionId;

    @NotNull(message = "Role name null")
    private RoleName roleName;
}
