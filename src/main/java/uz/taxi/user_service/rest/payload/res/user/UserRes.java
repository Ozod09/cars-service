package uz.taxi.user_service.rest.payload.res.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.taxi.user_service.enums.RoleName;
import uz.taxi.user_service.rest.payload.res.GetMeRes;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRes extends GetMeRes {

    private RoleName roleName;

    private String password;

    private UUID regionId;
}
