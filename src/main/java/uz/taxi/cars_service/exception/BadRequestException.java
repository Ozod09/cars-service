package uz.taxi.cars_service.exception;

import uz.taxi.cars_service.base.Message;

public class BadRequestException extends RuntimeException {
    public BadRequestException(Message message) {
        super(message.getText());
    }
}
