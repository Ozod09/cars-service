package uz.taxi.cars_service.exception;

import uz.taxi.cars_service.base.Message;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(Message message) {
        super(message.getText());
    }
}
