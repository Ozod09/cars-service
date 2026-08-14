package uz.taxi.user_service.config.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class CoreUtils {

    public static boolean isEmpty(String str) {
        return !StringUtils.hasText(str);
    }

    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    public static boolean isEmpty(Collection<?> col) {
        return col == null || col.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isPresent(String str) {
        return StringUtils.hasText(str);
    }

    public static boolean isPresent(Object obj) {
        return obj != null;
    }

    public static boolean isPresent(Collection<?> col) {
        return col != null && !col.isEmpty();
    }

    public static boolean isPresent(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    public static String generateOtpCode() {
       return String.valueOf((int) (Math.random() * ((999999 - 100000) + 1)) + 100000).substring(0, 5);
        // return "12345"; // todo fixme
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static String maskedPassword() {
        return "******";
    }

    public static String maskedPhone(String phone) {
        if (phone != null && phone.length() == 13) {
            return String.format("%s******%s", phone.substring(1, 5), phone.substring(11));
        } else if (phone != null && phone.length() == 12) {
            return String.format("%s******%s", phone.substring(0, 5), phone.substring(10));
        } else {
            return "";
        }
    }

    public static String getUsername(String authorization) {
        authorization = authorization.substring(6);
        byte[] decode = Base64.getDecoder().decode(authorization);
        String usernameAndPassword = new String(decode);
        String[] strings = usernameAndPassword.split(":");
        String username = strings[0];

        Assert.isTrue(strings.length == 2, "Username and password does not match");
        return username;
    }

    public static String toLogString(Object obj) {
        if (CoreUtils.isEmpty(obj)) {
            return null;
        }
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Throwable th) {
            return obj.toString();
        }
    }

    public static Boolean validatePhone(String phone) {
        return isPresent(phone) && phone.length() == 12 && phone.matches("\\d+");
    }

    public static Boolean validatePin(String pin) {
        return isPresent(pin) && pin.length() == 6 && pin.matches("\\d+");
    }
}
