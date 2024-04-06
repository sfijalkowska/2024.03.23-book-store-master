package pl.comarch.camp.it.book.store.dao;

import pl.comarch.camp.it.book.store.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    Optional<User> getById(int id);
    Optional<User> getByLogin(String login);
    List<User> getAll();
    @Deprecated
    void save(User user);
    void delete(int id);
    @Deprecated
    void update(User user);
    default void persist(User user) {
        throw new UnsupportedOperationException();
    }
}
