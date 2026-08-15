package uz.taxi.cars_service.connecter;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.PrematureCloseException;
import uz.taxi.cars_service.common.GlobalVar;
import uz.taxi.cars_service.config.helper.ErrorResponseUtils;
import uz.taxi.cars_service.config.helper.Utils;

import java.net.SocketTimeoutException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Slf4j
public class WebClientConnector {

    private final WebClient webClient;
    private final ConnectorSetting setting;

    public WebClientConnector(
            final WebClient webClient,
            final ConnectorSetting connectorSetting
    ) {
        this.webClient = webClient;
        this.setting = connectorSetting;
    }

    public <T> T doGet(String endpoint,
                       HttpHeaders httpHeaders,
                       Object request,
                       Class<T> responseClass,
                       T defaultResponse,
                       boolean throwOnTimeout,
                       Duration timeout,
                       boolean reqLogging,
                       boolean resLogging) throws ResourceAccessException {
        return this.doRequest(endpoint, httpHeaders, HttpMethod.GET, request, responseClass, defaultResponse, throwOnTimeout, timeout, reqLogging, resLogging);
    }

    public <T> T doPost(String endpoint,
                        HttpHeaders httpHeaders,
                        Object request,
                        Class<T> responseClass,
                        T defaultResponse,
                        boolean throwOnTimeout,
                        Duration timeout,
                        boolean reqLogging,
                        boolean resLogging) throws ResourceAccessException {
        return this.doRequest(endpoint, httpHeaders, HttpMethod.POST, request, responseClass, defaultResponse, throwOnTimeout, timeout, reqLogging, resLogging);
    }

    public <T> T doPatch(String endpoint,
                        HttpHeaders httpHeaders,
                        Object request,
                        Class<T> responseClass,
                        T defaultResponse,
                        boolean throwOnTimeout,
                        Duration timeout,
                        boolean reqLogging,
                        boolean resLogging) throws ResourceAccessException {
        return this.doRequest(endpoint, httpHeaders, HttpMethod.PATCH, request, responseClass, defaultResponse, throwOnTimeout, timeout, reqLogging, resLogging);
    }

    private <T> T doRequest(String endpoint, HttpHeaders httpHeaders, HttpMethod httpMethod, Object request, Class<T> responseClass, T defaultResponse, boolean throwOnTimeout, Duration timeout,  boolean reqLogging, boolean resLogging) throws ResourceAccessException {

        HttpHeaders headers = new HttpHeaders();
        headers.addAll(setting.getHeaders());
        headers.addAll(httpHeaders);

        final String logId = GlobalVar.getLogId();
        final UUID userId = GlobalVar.getUserId();

        final String url = setting.getUrl(endpoint);
        final String externalId = StringUtils.hasText(logId) ? logId : Utils.generateId();

        final Map<String, String> forThreadContextMap = Utils.isEmpty(ThreadContext.getContext()) ? new HashMap<>() : ThreadContext.getContext();

        final long startTime = System.currentTimeMillis();

        try {
            if (reqLogging) { // Request Log
                log.info("LOG-ID:{} URL:{} METHOD:{} HEADERS:{} BODY:{} USER:{}", externalId, url, httpMethod, headers, Utils.toLogString(request), userId);
            }

            ResponseEntity<T> responseEntity = webClient.method(httpMethod)
                    .uri(url)
                    .headers(customHeaders -> customHeaders.addAll(headers))
                    .bodyValue(request)
                    .retrieve()
                    .toEntity(responseClass)
                    .timeout(timeout)
                    .doOnSuccess(res -> {
                        if (resLogging) { // Response Log
                            ThreadContext.putAll(forThreadContextMap); // must be set before sending to the logger
                            log.info("LOG-ID:{} STATUS:{} TIME:{} HEADERS:{} BODY:{} USER:{}", externalId, res.getStatusCode(), System.currentTimeMillis() - startTime, headers, Utils.toLogString(res), userId);
                        }
                    })
                    .doOnError(throwable -> {
                        HttpStatus status = ErrorResponseUtils.getStatus(throwable);
                        if (resLogging) {  // Response Error Log
                            String message = ErrorResponseUtils.getMessage(throwable);
                            ThreadContext.putAll(forThreadContextMap); // must be set before sending to the logger
                            log.info("LOG-ID:{} STATUS:{} TIME:{} HEADERS:{} BODY:{} USER:{}", externalId, status, System.currentTimeMillis() - startTime, headers, message, userId);
                        }
                        if (throwOnTimeout) {
                            if (status.is4xxClientError()) {
                                throw new ResourceAccessException("Connection close");
                            }
                            if (throwable instanceof ResourceAccessException) {
                                throw (ResourceAccessException) throwable;
                            } else if (throwable instanceof SocketTimeoutException) {
                                throw new ResourceAccessException("Service not available");
                            } else if (throwable instanceof TimeoutException) {
                                throw new ResourceAccessException("Service not available");
                            } else if (throwable instanceof PrematureCloseException) {
                                throw new ResourceAccessException("Service not available");
                            }
                        }
                    })
                    .block();

            return Objects.requireNonNull(responseEntity).getBody();

        } catch (HttpStatusCodeException ex) {
            log.error(ex.getMessage());
            if (ex instanceof HttpServerErrorException) {
                throw ex;
            }
        } catch (WebClientException ex) {
            log.error(ex.getMessage());
            if (ex instanceof WebClientResponseException) {
                throw ex;
            }
        }

        return defaultResponse;
    }
}
