package uz.taxi.user_service.rest.payload.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReqPhone {

    @NotNull(message = "Invalid is phone")
    @Pattern(
            regexp = "^\\+998\\d{9}$",
            message = "Phone number must be in format +998XXXXXXXXX"
    )
    private String phone;
}
