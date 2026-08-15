package uz.taxi.cars_service.connecter;

import lombok.Getter;

@Getter
public enum IntServiceURL {

    // user-service
    STUDENT_CREATE("", "/api/v1/user/student/create"),
    SAVE_TRUSTED_DEVICE("", "/api/v1/user/device/trusted/save"),
    
    // notify
    SMS_SEND("","/api/v1/notification/sms/send"),
    USER_INFO("", "/api/v1/notification/user/info/save");

    private final String method;
    private final String url;

    IntServiceURL(String method, String url) {
        this.method = method;
        this.url = url;
    }
}
