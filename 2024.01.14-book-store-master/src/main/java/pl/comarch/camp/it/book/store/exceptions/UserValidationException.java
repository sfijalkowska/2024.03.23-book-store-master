package pl.comarch.camp.it.book.store.exceptions;

public class UserValidationException extends RuntimeException {

    public UserValidationException(String message) {
        super(message);
    }
}
