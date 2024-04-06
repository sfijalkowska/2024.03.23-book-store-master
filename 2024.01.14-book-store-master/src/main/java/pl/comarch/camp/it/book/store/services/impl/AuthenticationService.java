package pl.comarch.camp.it.book.store.services.impl;

import jakarta.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.comarch.camp.it.book.store.dao.IUserDAO;
import pl.comarch.camp.it.book.store.model.User;
import pl.comarch.camp.it.book.store.model.dto.RegisterUserDTO;
import pl.comarch.camp.it.book.store.services.IAuthenticationService;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    HttpSession httpSession;
    private final IUserDAO userDAO;

    public AuthenticationService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void login(String login, String password) {
        this.userDAO.getByLogin(login).ifPresent(user -> {
            if(user.getPassword().equals(DigestUtils.md5Hex(password))) {
                user.setPassword(null);
                httpSession.setAttribute("user", user);
            }
        });
    }

    @Override
    public void logout() {
        httpSession.removeAttribute("user");
    }

    @Override
    public void register(RegisterUserDTO userDTO) {
        this.userDAO.save(map(userDTO));
    }

    private User map(RegisterUserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setLogin(userDTO.getLogin());
        user.setPassword(DigestUtils.md5Hex(userDTO.getPassword()));
        user.setRole(User.Role.USER);

        return user;
    }
}
