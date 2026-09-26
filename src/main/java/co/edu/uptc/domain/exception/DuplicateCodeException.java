package co.edu.uptc.domain.exception;

public class DuplicateCodeException extends RuntimeException{
    public DuplicateCodeException(String message) {
        super(message);
    }
}
