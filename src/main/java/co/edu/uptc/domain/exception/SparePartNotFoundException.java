package co.edu.uptc.domain.exception;

public class SparePartNotFoundException extends RuntimeException{
    public SparePartNotFoundException(String message){
        super(message);
    }
}
