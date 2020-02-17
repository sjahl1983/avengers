package mx.sjahl.avengers.exception;

public class MarvelException extends Exception {

    public MarvelException(String message) {
        super(message);
    }

    public MarvelException(Throwable cause) {
        super(cause);
    }
}
