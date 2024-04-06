package pl.comarch.camp.it.book.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.comarch.camp.it.book.store.exceptions.InvalidCartException;
import pl.comarch.camp.it.book.store.services.IOrderService;

@Controller
@RequestMapping(path = "/order")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.GET)
    public String confirm() {
        try {
            this.orderService.confirm();
            return "redirect:/main";
        } catch (InvalidCartException e) {
            return "redirect:/cart";
        }
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String orders(Model model) {
        model.addAttribute("orders", this.orderService.getCurrentUserOrders());
        return "orders";
    }
}
