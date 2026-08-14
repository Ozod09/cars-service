package uz.taxi.user_service.rest.payload.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetMeRes {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phone;
    private String attachmentPath;
    private String status;
}
