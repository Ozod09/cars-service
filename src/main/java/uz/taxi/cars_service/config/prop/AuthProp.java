package uz.taxi.cars_service.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProp {

    private List<User> users;

    @Data
    public static class User {
        private String username;
        private String password;
        private String role;
    }
}
