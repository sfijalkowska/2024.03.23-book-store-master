package pl.comarch.camp.it.book.store.exceptions;

public class BookValidationException extends RuntimeException {

    public BookValidationException(String message) {
        super(message);
    }
}
