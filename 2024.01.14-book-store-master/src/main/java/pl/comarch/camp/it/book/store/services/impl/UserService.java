package pl.comarch.camp.it.book.store.services.impl;

import org.springframework.stereotype.Service;
import pl.comarch.camp.it.book.store.dao.IUserDAO;
import pl.comarch.camp.it.book.store.model.User;
import pl.comarch.camp.it.book.store.services.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserDAO userDAO;

    public UserService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> getById(int id) {
        return this.userDAO.getById(id);
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return this.userDAO.getByLogin(login);
    }

    @Override
    public List<User> getAll() {
        return this.userDAO.getAll();
    }

    @Override
    public void delete(int id) {
        this.userDAO.delete(id);
    }
}
