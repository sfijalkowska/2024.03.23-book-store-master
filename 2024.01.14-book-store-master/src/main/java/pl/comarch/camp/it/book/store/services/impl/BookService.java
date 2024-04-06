package pl.comarch.camp.it.book.store.services.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.comarch.camp.it.book.store.dao.IBookDAO;
import pl.comarch.camp.it.book.store.dao.IUserDAO;
import pl.comarch.camp.it.book.store.model.Book;
import pl.comarch.camp.it.book.store.model.User;
import pl.comarch.camp.it.book.store.model.dto.rest.BookDTO;
import pl.comarch.camp.it.book.store.services.IBookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    @Autowired
    HttpSession httpSession;
    private final IBookDAO bookDAO;

    private final IUserDAO userDAO;

    public BookService(IBookDAO bookDAO, IUserDAO userDAO) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void save(Book book) {
        book.setCreator((User) this.httpSession.getAttribute("user"));
        this.bookDAO.save(book);
    }

    @Override
    public Optional<Book> getById(int id) {
        return this.bookDAO.getById(id);
    }

    @Override
    public void update(int id, Book book) {
        book.setId(id);
        book.setCreator((User) this.httpSession.getAttribute("user"));
        this.bookDAO.update(book);
    }

    @Override
    public BookDTO update(int id, BookDTO bookDTO) {
        Book book = bookDTO.toBook(this.userDAO);
        book.setId(id);
        this.bookDAO.persist(book);
        return new BookDTO(book);
    }

    @Override
    public List<Book> getByPattern(String pattern) {
        return this.bookDAO.getByPattern(pattern);
    }

    @Override
    public List<Book> getAll() {
        return this.bookDAO.getAll();
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        Book book = bookDTO.toBook(this.userDAO);
        this.bookDAO.persist(book);
        return new BookDTO(book);
    }
}
