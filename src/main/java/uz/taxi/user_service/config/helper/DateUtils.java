package uz.taxi.user_service.config.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static DateTimeFormatter fLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime identityTokenExpire() {
        return LocalDateTime.now().plusMinutes(10L);
    }

    public static LocalDateTime otpExpire() {
        return LocalDateTime.now().plusMinutes(1L);
    }

    public static String dateTimeToFront(LocalDateTime dateTime) {
        return fLocalDateTime.format(dateTime);
    }
}
