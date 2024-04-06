package pl.comarch.camp.it.book.store.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.comarch.camp.it.book.store.services.IBookService;

@Controller
public class CommonController {
    private final IBookService bookService;

    public CommonController(IBookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(path = {"/main", "/", "/index"}, method = RequestMethod.GET)
    public String main(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("filter") instanceof String) {
            String pattern = (String) httpSession.getAttribute("filter");
            model.addAttribute("books", this.bookService.getByPattern(pattern));
        } else {
            model.addAttribute("books", this.bookService.getAll());
        }
        return "index";
    }

    @RequestMapping(path = "/filter", method = RequestMethod.GET)
    public String filter(@RequestParam String pattern, HttpSession httpSession) {
        if(pattern.isEmpty()) {
            httpSession.removeAttribute("filter");
        } else {
            httpSession.setAttribute("filter", pattern);
        }
        return "redirect:/main";
    }

    @RequestMapping(path = "/contact", method = RequestMethod.GET)
    public String contact() {
        return "contact";
    }
}
