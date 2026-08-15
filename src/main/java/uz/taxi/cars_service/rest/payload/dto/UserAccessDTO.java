package uz.taxi.cars_service.rest.payload.dto;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessDTO {

    private String accessToken;

    private Long accessTokenExpire;

    private String refreshToken;

    private Long refreshTokenExpire;
}
