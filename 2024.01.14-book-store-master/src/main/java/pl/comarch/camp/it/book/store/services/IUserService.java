package pl.comarch.camp.it.book.store.services;

import pl.comarch.camp.it.book.store.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> getById(int id);
    Optional<User> getByLogin(String login);
    List<User> getAll();
    void delete(int id);
}
