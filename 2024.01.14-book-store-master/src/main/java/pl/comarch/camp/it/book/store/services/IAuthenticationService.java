package pl.comarch.camp.it.book.store.services;

import pl.comarch.camp.it.book.store.model.dto.RegisterUserDTO;

public interface IAuthenticationService {
    void login(String login, String password);
    void logout();
    void register(RegisterUserDTO userDTO);
}
