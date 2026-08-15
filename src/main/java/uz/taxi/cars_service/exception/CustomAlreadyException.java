package uz.taxi.cars_service.exception;

public class CustomAlreadyException extends RuntimeException {
    public CustomAlreadyException(String message) {
        super(message);
    }
}
