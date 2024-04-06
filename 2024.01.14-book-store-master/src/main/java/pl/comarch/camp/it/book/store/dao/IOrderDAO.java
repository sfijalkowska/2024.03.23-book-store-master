package pl.comarch.camp.it.book.store.dao;

import pl.comarch.camp.it.book.store.model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderDAO {
    Optional<Order> getById(int id);
    List<Order> getAll();
    @Deprecated
    void save(Order order);
    @Deprecated
    void update(Order order);
    default void persist(Order order) {
        throw new UnsupportedOperationException();
    }
    void delete(int id);
    List<Order> getByUserId(int userId);
}
