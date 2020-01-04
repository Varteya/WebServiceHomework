package Exceptions;

public class EntityDoesNotExistsException extends Exception {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EntityDoesNotExistsException(String message) {
        this.message = message;
    }

    public EntityDoesNotExistsException(String message, String message1) {
        super(message);
        this.message = message1;
    }
}
