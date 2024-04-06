package pl.comarch.camp.it.book.store.dao;

import pl.comarch.camp.it.book.store.model.Book;

import java.util.List;
import java.util.Optional;

public interface IBookDAO {
    Optional<Book> getById(int id);
    List<Book> getAll();
    List<Book> getByPattern(String pattern);
    @Deprecated
    void save(Book book);
    @Deprecated
    void update(Book book);
    default void persist(Book book) {
        throw new UnsupportedOperationException();
    }
    void delete(int id);
}
