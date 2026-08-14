package uz.taxi.user_service.base;

public interface BaseURL {

    String DOC_OPEN_API = "/v3/api-docs/**";

    String API1 = "/api/v1";

    String CLIENT = "/client";
    String USER = "/user";
    String DRIVER = "/driver";


    String INFO = "/info";

    String LOGIN = "/login";

    String CODE = "/code";
    String SEND = "/send";
    String RESEND = "/resend";
    String VERIFY = "/verify";

    String REFRESH_TOKEN = "/refresh-token";

    String CHECK_PHONE = "/check-phone";

    String CHECK_USER_PHONE = "/check-user-phone";
    String UPDATE_PASSWORD = "/update-password";
    String DELETE_ACCOUNT = "/delete/account";
}
