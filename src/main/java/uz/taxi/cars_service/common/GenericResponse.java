package uz.taxi.cars_service.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import uz.taxi.cars_service.enums.ActionTypeEnum;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public GenericResponse(String message, T data) {
        this.status = ActionTypeEnum.SUCCESS.name();
        this.message = message;
        this.data = data;
    }

    public GenericResponse(String message) {
        this.status = ActionTypeEnum.ERROR.name();
        this.message = message;
    }

    public static <T> GenericResponse<T> success(String message, T data) {
        return new GenericResponse<>(message, data);
    }


    public static <T> GenericResponse<T> error(String message) {
        return new GenericResponse<>(message);
    }
}
