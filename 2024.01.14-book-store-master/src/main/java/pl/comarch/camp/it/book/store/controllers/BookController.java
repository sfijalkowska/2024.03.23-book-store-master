package pl.comarch.camp.it.book.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.comarch.camp.it.book.store.exceptions.BookValidationException;
import pl.comarch.camp.it.book.store.model.Book;
import pl.comarch.camp.it.book.store.services.IBookService;
import pl.comarch.camp.it.book.store.validators.BookValidator;

import java.util.Optional;

@Controller
@RequestMapping(path = "/book")
public class BookController {
    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("bookModel", new Book());
        return "book-form";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute Book book) {
        try {
            BookValidator.validateBook(book);
        } catch (BookValidationException e) {
            e.printStackTrace();
            return "redirect:/book/add";
        }
        this.bookService.save(book);
        return "redirect:/main";
    }

    @RequestMapping(path = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable int id, Model model) {
        Optional<Book> bookBox = this.bookService.getById(id);
        if(bookBox.isEmpty()) {
            return "redirect:/main";
        }
        model.addAttribute("bookModel", bookBox.get());
        return "book-form";
    }

    @RequestMapping(path = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable int id, @ModelAttribute Book book) {
        this.bookService.update(id, book);
        return "redirect:/main";
    }
}
