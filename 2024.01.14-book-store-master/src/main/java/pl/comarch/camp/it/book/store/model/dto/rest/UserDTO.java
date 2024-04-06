package pl.comarch.camp.it.book.store.model.dto.rest;

import lombok.*;
import pl.comarch.camp.it.book.store.dao.IBookDAO;
import pl.comarch.camp.it.book.store.dao.IUserDAO;
import pl.comarch.camp.it.book.store.model.User;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private int id;
    private String login;
    private String name;
    private String surname;
    private User.Role role;
    private Set<OrderDTO> orders = new HashSet<>();

    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.role = user.getRole();
        this.orders.addAll(user.getOrders().stream().map(OrderDTO::new).toList());
    }

    public User toUser(IUserDAO userDAO, IBookDAO bookDAO) {
        User user = new User();
        user.setId(this.id);
        user.setLogin(this.login);
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setRole(this.role);
        user.getOrders().addAll(this.orders.stream()
                .map(orderDTO -> orderDTO.toOrder(userDAO, bookDAO)).toList());
        return user;
    }
}
