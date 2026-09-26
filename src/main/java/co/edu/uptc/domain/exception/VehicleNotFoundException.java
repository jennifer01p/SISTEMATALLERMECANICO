package co.edu.uptc.domain.exception;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String message) {
        super(message);
    }
}