package uz.taxi.cars_service.config.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@RequiredArgsConstructor
public class ErrorResponseUtils {

    public static HttpStatus getStatus(Throwable throwable) {
        if (throwable instanceof WebClientResponseException) {
            return getStatusFromWebClient((WebClientResponseException) throwable);
        } else if (throwable instanceof RuntimeException) {
            return getStatusFromRuntime((RuntimeException) throwable);
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private static HttpStatus getStatusFromRuntime(RuntimeException throwable) {
        if (throwable.getMessage().startsWith("404")) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private static HttpStatus getStatusFromWebClient(WebClientResponseException throwable) {
        return (HttpStatus) throwable.getStatusCode();
    }

    public static String getMessage(Throwable throwable) {
        if (throwable instanceof WebClientResponseException) {
            return getMessageFromWebClient((WebClientResponseException) throwable);
        } else if (throwable instanceof RuntimeException) {
            return getMessageFromRuntime((RuntimeException) throwable);
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        }
    }

    private static String getMessageFromWebClient(WebClientResponseException throwable) {
        return throwable.getMessage();
    }

    private static String getMessageFromRuntime(RuntimeException e) {
        return e.getMessage();
    }
}
