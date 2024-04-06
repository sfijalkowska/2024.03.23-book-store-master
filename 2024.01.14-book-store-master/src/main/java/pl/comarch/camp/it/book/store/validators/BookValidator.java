package pl.comarch.camp.it.book.store.validators;

import pl.comarch.camp.it.book.store.exceptions.BookValidationException;
import pl.comarch.camp.it.book.store.model.Book;

public class BookValidator {

    public static void validateTitle(String title) {
        String regex = "^.+$";
        if(!title.matches(regex)) {
            throw new BookValidationException("Incorrect title");
        }
    }

    public static void validateAuthor(String author) {
        String regex = "^[A-Z][a-z]+\\ [A-Z][a-z]+([-\\ ][A-Z][a-z]+)?(,(\\ )?[A-Z][a-z]+\\ [A-Z][a-z]+([-\\ ][A-Z][a-z]+)?)*$";
        if(!author.matches(regex)) {
            throw new BookValidationException("Incorrect author");
        }
    }

    public static void validateISBN(String isbn) {
        String regex = "^(978|979)-[0-9]{2}-[0-9]{2,6}-[0-9]{1,5}-[0-9]$";
        if(!isbn.matches(regex)) {
            throw new BookValidationException("Incorrect ISBN");
        }
    }

    public static void validatePrice(double price) {
        if(price <= 0) {
            throw new BookValidationException("Price can not be negative or zero");
        }
    }

    public static void validateQuantity(int quantity) {
        if(quantity < 0) {
            throw new BookValidationException("Quantity can not be negative");
        }
    }

    public static void validateBook(Book book) {
        validateTitle(book.getTitle());
        validateAuthor(book.getAuthor());
        validateISBN(book.getIsbn());
        validatePrice(book.getPrice().doubleValue());
        validateQuantity(book.getQuantity());
    }
}
