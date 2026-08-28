package uz.taxi.cars_service.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import uz.taxi.cars_service.base.Message;
import uz.taxi.cars_service.enums.ActionTypeEnum;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> {

    @JsonProperty("status")
    private Message status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public GenericResponse(Message message, T data) {
        this.status = Message.SUCCESS;
        this.message = message.getText();
        this.data = data;
    }

    public GenericResponse(Message message) {
        this.status = message;
        this.message = message.getText();
    }

    public static <T> GenericResponse<T> success(Message message, T data) {
        return new GenericResponse<>(message, data);
    }


    public static <T> GenericResponse<T> error(Message message) {
        return new GenericResponse<>(message);
    }
}
