package uz.taxi.user_service.config.prop;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(value = "internal")
public class InternalProp {

    private InternalSVC user;
    private InternalSVC notify;

    @Getter
    @Setter
    public static class InternalSVC {
        private String url;
        private String username;
        private String password;
    }
}
