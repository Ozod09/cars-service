package uz.taxi.user_service.exception;

public class CustomAlreadyException extends RuntimeException {
    public CustomAlreadyException(String message) {
        super(message);
    }
}
