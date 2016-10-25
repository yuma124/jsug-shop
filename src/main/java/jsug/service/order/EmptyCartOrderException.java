package jsug.service.order;

public class EmptyCartOrderException extends RuntimeException {
    public EmptyCartOrderException(String message) {
        super(message);
    }
}
