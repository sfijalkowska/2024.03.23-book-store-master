package pl.comarch.camp.it.book.store.exceptions;

public class BookAlreadyExistException extends RuntimeException {

    public BookAlreadyExistException(String message) {
        super(message);
    }
}
