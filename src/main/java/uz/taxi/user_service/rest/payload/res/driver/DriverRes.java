package uz.taxi.user_service.rest.payload.res.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.taxi.user_service.rest.payload.res.GetMeRes;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverRes extends GetMeRes {

    private LocalDate birthDate;

    private String passportSerialNumber;

    private String passportPhotoPath;
}
