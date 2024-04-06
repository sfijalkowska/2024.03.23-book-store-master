package pl.comarch.camp.it.book.store.dao;

import pl.comarch.camp.it.book.store.model.Position;

import java.util.List;

public interface IPositionDAO {
    List<Position> getByOrderId(int orderId);
    void save(Position position, int orderId);
    void update(Position position);
    void delete(int positionId);
}
