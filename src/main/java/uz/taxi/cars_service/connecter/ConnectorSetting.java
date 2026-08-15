package uz.taxi.cars_service.connecter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public interface ConnectorSetting {

    void clearHeader();

    void addHeader(String name, String value);

    void removeHeader(String name);

    HttpHeaders getHeaders();

    String getUrl(String endpoint);

    void authBearer(String token);

    void authBasic(String username, String password);

    void setContentType(MediaType mediaType);
}
