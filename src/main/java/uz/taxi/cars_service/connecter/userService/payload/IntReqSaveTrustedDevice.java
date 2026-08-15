package uz.taxi.cars_service.connecter.userService.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class IntReqSaveTrustedDevice {

    private UUID uuid;
    private UUID userId;
    private String deviceId;
    private String deviceModel;
}
