package uz.taxi.cars_service.connecter.userService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import uz.taxi.cars_service.config.helper.Utils;
import uz.taxi.cars_service.config.prop.InternalProp;
import uz.taxi.cars_service.connecter.ConnectorSetting;

@Component("userServiceSetting")
public class IntUserServiceSetting implements ConnectorSetting {

    private final HttpHeaders httpHeaders;
    private final InternalProp internalProp;

    public IntUserServiceSetting(InternalProp internalProp) {
        this.internalProp = internalProp;
        this.httpHeaders = new HttpHeaders();
        this.clearHeader();
    }

    @Override
    public void clearHeader() {
        this.httpHeaders.clear();
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        this.httpHeaders.setBasicAuth(
                this.internalProp.getUser().getUsername(),
                this.internalProp.getUser().getPassword()
        );
    }

    @Override
    public void addHeader(String name, String value) {
        if (Utils.isPresent(this.httpHeaders.get(name))) {
            this.httpHeaders.remove(name);
        }
        this.httpHeaders.add(name, value);
    }

    @Override
    public void removeHeader(String name) {
        if (Utils.isPresent(this.httpHeaders.get(name))) {
            this.httpHeaders.remove(name);
        }
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.httpHeaders;
    }

    @Override
    public String getUrl(String endpoint) {
        return this.internalProp.getUser().getUrl() + endpoint;
    }

    @Override
    public void authBearer(String token) {
        this.httpHeaders.setBearerAuth(token);
    }

    @Override
    public void authBasic(String username, String password) {
        this.httpHeaders.setBasicAuth(username, password);
    }

    @Override
    public void setContentType(MediaType mediaType) {
        this.httpHeaders.setContentType(mediaType);
    }
}
