package pl.comarch.camp.it.book.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.comarch.camp.it.book.store.services.ICartService;

@Controller
@RequestMapping(path = "/cart")
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping(path = "/add/{id}", method = RequestMethod.GET)
    public String add(@PathVariable int id) {
        cartService.addBook(id);
        return "redirect:/main";
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String cart() {
        return "cart";
    }

    @RequestMapping(path = "/remove/{bookId}", method = RequestMethod.GET)
    public String remove(@PathVariable int bookId) {
        this.cartService.removeBook(bookId);
        return "redirect:/cart";
    }
}
