package pl.comarch.camp.it.book.store.model.dto.rest;

import lombok.*;
import pl.comarch.camp.it.book.store.dao.IBookDAO;
import pl.comarch.camp.it.book.store.dao.IUserDAO;
import pl.comarch.camp.it.book.store.exceptions.UserNotExistException;
import pl.comarch.camp.it.book.store.model.Order;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {
    private int id;
    private LocalDateTime date;
    private Order.Status status;
    private double total;
    private int userId;
    private final Set<PositionDTO> positions = new HashSet<>();

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.date = order.getDate();
        this.status = order.getStatus();
        this.total = order.getTotal();
        this.userId = order.getUser().getId();
        this.positions.addAll(order.getPositions().stream().map(PositionDTO::new).toList());
    }

    public Order toOrder(IUserDAO userDAO, IBookDAO bookDAO) {
        Order order = new Order();
        order.setId(this.id);
        order.setDate(this.date);
        order.setStatus(this.status);
        order.setTotal(this.total);
        order.setUser(userDAO.getById(this.userId).orElseThrow(UserNotExistException::new));
        order.getPositions().addAll(this.positions.stream().map(positionDTO -> positionDTO.toPosition(bookDAO)).toList());
        return order;
    }
}
