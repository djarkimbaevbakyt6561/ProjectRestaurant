package peaksoft.exceptions;


public class AuthorizeException extends RuntimeException {
    public AuthorizeException() {
        super();
    }

    public AuthorizeException(String message) {
        super(message);
    }
}
