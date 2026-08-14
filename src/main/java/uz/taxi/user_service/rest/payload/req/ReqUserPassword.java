package uz.taxi.user_service.rest.payload.req;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReqUserPassword {
    
    private UUID userId;
    private String oldPassword;
    private String newPassword;
    private String prePassword;
}
