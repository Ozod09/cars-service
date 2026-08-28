package uz.taxi.cars_service.base;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Message {

    SUCCESS("success"),
    DELETED_SUCCESSFULLY("Muvaffaqiyatli o’chirildi"),


    TYPE_SERVICE_NOT_FOUND("Type of service not found"),
    CARCLASS_NOT_FOUND("CarsClass topilmadi"),
    DATA_NOT_FOUND("Maʼlumotlar topilmadi"),
    TARIFF_NOT_FOUND("Tariff topilmaid"),
    CAR_NOT_FOUND("Car topilmadi"),

    AUTHENTICATION_REQUIRED("Authentication required"),
    FORBIDDEN("API ga ruxsat berilmagan"),

    CAR_REDIS_ID("Car ID yaroqsiz"),

    INVALID_DRIVER_ID("Driver ID yaroqsiz"),

    INVALID_STATUS_VALUE("Invalid status value"),
    INVALID_BRENT_STATUS("Invalid brent status value"),

    UNKNOWN_ERROR("Noma’lum xato");

    private final String text;
}
