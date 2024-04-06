package pl.comarch.camp.it.book.store.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.comarch.camp.it.book.store.model.User;
import pl.comarch.camp.it.book.store.model.dto.rest.BookDTO;
import pl.comarch.camp.it.book.store.model.dto.rest.UserDTO;
import pl.comarch.camp.it.book.store.model.dto.rest.UserListDTO;
import pl.comarch.camp.it.book.store.services.IUserService;

import java.util.List;

@RestController
@RequestMapping(path = Constants.API_BASE_URL + "/user")
public class RestUserController {

    private final IUserService userService;

    public RestUserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/{idOrLogin}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getByIdOrLogin(@PathVariable String idOrLogin) {
        if(idOrLogin.matches("^[0-9]+$")) {
            int id = Integer.parseInt(idOrLogin);
            return this.userService.getById(id)
                    .map(user -> ResponseEntity.ok(new UserDTO(user)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        return this.userService.getByLogin(idOrLogin)
                .map(user -> ResponseEntity.ok(new UserDTO(user)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public UserListDTO getAll() {
        List<User> users = this.userService.getAll();
        UserListDTO userListDTO = new UserListDTO();
        userListDTO.getUsers().addAll(users.stream().map(UserDTO::new).toList());
        return userListDTO;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        this.userService.delete(id);
    }
}
