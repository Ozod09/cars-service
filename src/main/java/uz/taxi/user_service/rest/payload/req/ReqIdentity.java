package uz.taxi.user_service.rest.payload.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReqIdentity {

    @NotNull(message = "Invalid is identity")
    private UUID identity;
}
