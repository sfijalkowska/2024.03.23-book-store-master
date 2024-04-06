package pl.comarch.camp.it.book.store.services;

public interface ICartService {
    void addBook(int bookId);
    void removeBook(int bookId);
}
