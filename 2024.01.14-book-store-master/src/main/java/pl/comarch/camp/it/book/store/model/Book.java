package pl.comarch.camp.it.book.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Book implements Cloneable {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private int quantity;
    private User creator;

    public Book(int id, String title, String author, String isbn, double price, int quantity, User creator) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = new BigDecimal(price);
        this.quantity = quantity;
        this.creator = creator;
    }

    public Book(int id, String title, String author, String isbn, double price, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = new BigDecimal(price);
        this.quantity = quantity;
    }

    public Book(int id) {
        this.id = id;
    }

    @Override
    public Book clone() {
        /*try {
            Book clone = (Book) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }*/
        Book book = new Book();
        book.setId(this.id);
        book.setTitle(this.title);
        book.setAuthor(this.author);
        book.setIsbn(this.isbn);
        //book.setPrice(this.price);
        book.price = this.price;
        book.setQuantity(this.quantity);

        return book;
    }

    public void setPrice(double price) {
        this.price = new BigDecimal(price);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
