package uz.taxi.cars_service.connecter.userService.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReqPermission {
    
    @JsonProperty("userId")
    private UUID userId;
}
