package uz.taxi.cars_service.common;

import lombok.Getter;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpHeaders;
import uz.taxi.cars_service.base.HeaderKeys;
import uz.taxi.cars_service.config.helper.Utils;

import java.util.UUID;

public class GlobalVar {

    @Getter
    private final static GlobalVar INSTANCE = new GlobalVar();

    private final static ThreadLocal<String> H_IP_ADDRESS = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_USER_AGENT = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_APP_VERSION = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<Boolean> H_DEBUG_VERSION = ThreadLocal.withInitial(() -> Boolean.FALSE);
    private final static ThreadLocal<String> H_DEVICE_MODEL = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_DEVICE_ID = ThreadLocal.withInitial(String::new);

    private final static ThreadLocal<UUID> USER_ID = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<String> LOG_ID = ThreadLocal.withInitial(String::new);

    public static String getIpAddress() {
        return H_IP_ADDRESS.get();
    }

    public static void setIpAddress(String ipAddress) {
        GlobalVar.H_IP_ADDRESS.set(ipAddress);
    }

    public static String getUserAgent() {
        return GlobalVar.H_USER_AGENT.get();
    }

    public static void setUserAgent(String userAgent) {
        GlobalVar.H_USER_AGENT.set(userAgent);
    }

    public static String getAppVersion() {
        return GlobalVar.H_APP_VERSION.get();
    }

    public static void setAppVersion(String appVersion) {
        GlobalVar.H_APP_VERSION.set(appVersion);
    }

    public static Boolean isDebugVersion() {
        return GlobalVar.H_DEBUG_VERSION.get();
    }

    public static void setDebugVersion(Boolean isDebugVersion) {
        GlobalVar.H_DEBUG_VERSION.set(isDebugVersion);
    }

    public static String getDeviceModel() {
        return GlobalVar.H_DEVICE_MODEL.get();
    }

    public static void setDeviceModel(String deviceModel) {
        GlobalVar.H_DEVICE_MODEL.set(deviceModel);
    }

    public static String getDeviceId() {
        return GlobalVar.H_DEVICE_ID.get();
    }

    public static void setDeviceId(String deviceId) {
        GlobalVar.H_DEVICE_ID.set(deviceId);
    }

    public static UUID getUserId() {
        return GlobalVar.USER_ID.get();
    }

    public static void setUserId(String userId) {
        if (Utils.isPresent(userId)) {
            GlobalVar.USER_ID.set(UUID.fromString(userId));
        }
    }

    public static String getLogId() {
        return GlobalVar.LOG_ID.get();
    }

    public static void setLogId(String logId) {
        GlobalVar.LOG_ID.set(logId);
    }

    public static void clearContext() {
        // header
        GlobalVar.H_IP_ADDRESS.remove();
        GlobalVar.H_USER_AGENT.remove();
        GlobalVar.H_APP_VERSION.remove();
        GlobalVar.H_DEBUG_VERSION.remove();
        GlobalVar.H_DEVICE_MODEL.remove();
        GlobalVar.H_DEVICE_ID.remove();
        // user
        GlobalVar.USER_ID.remove();
        GlobalVar.LOG_ID.remove();
        // clear log
        ThreadContext.clearAll();
    }

    public static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HeaderKeys.IP, getIpAddress());
        headers.add(HeaderKeys.USER_AGENT, getUserAgent());
        headers.add(HeaderKeys.APP_VERSION, getAppVersion());
        headers.add(HeaderKeys.DEBUG_VERSION, isDebugVersion() ? "1" : "0");
        headers.add(HeaderKeys.DEVICE_MODEL, getDeviceModel());
        headers.add(HeaderKeys.DEVICE_ID, getDeviceId());
        headers.add(HeaderKeys.USER_ID, Utils.isPresent(getUserId()) ? getUserId().toString() : "");
        headers.add(HeaderKeys.LOG_ID, getLogId());
        return headers;
    }
}
