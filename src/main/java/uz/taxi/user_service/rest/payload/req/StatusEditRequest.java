package uz.taxi.user_service.rest.payload.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StatusEditRequest {
    @NotNull(message = "Id null")
    private UUID id;

    @NotBlank(message = "Status null")
    private String userStatus;
}
