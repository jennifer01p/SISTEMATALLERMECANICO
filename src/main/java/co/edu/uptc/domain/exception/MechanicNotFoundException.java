package co.edu.uptc.domain.exception;

public class MechanicNotFoundException extends RuntimeException {
    public MechanicNotFoundException(String message) {
        super(message);
    }
}
