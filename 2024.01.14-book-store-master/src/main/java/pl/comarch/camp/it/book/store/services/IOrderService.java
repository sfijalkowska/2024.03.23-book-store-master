package pl.comarch.camp.it.book.store.services;

import pl.comarch.camp.it.book.store.model.Order;

import java.util.List;

public interface IOrderService {
    void confirm();
    List<Order> getCurrentUserOrders();
}
