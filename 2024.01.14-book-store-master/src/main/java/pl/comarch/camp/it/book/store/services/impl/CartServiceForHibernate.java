package pl.comarch.camp.it.book.store.services.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.comarch.camp.it.book.store.dao.IBookDAO;
import pl.comarch.camp.it.book.store.dao.IUserDAO;
import pl.comarch.camp.it.book.store.model.Book;
import pl.comarch.camp.it.book.store.model.Position;
import pl.comarch.camp.it.book.store.model.User;
import pl.comarch.camp.it.book.store.services.ICartService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceForHibernate implements ICartService {
    @Autowired
    HttpSession httpSession;

    private IBookDAO bookDAO;

    private IUserDAO userDAO;

    public CartServiceForHibernate(IBookDAO bookDAO, IUserDAO userDAO) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void addBook(final int bookId) {
        final Optional<Book> bookBox = this.bookDAO.getById(bookId);

        if(bookBox.isEmpty()) {
            return;
        }

        final User user = (User) this.httpSession.getAttribute("user");
        user.getCart().stream()
                .filter(p -> p.getBook().getId() == bookId)
                .findFirst()
                .ifPresentOrElse(
                        Position::incrementQuantity,
                        () -> {
                            Position newPosition = new Position();
                            newPosition.setBook(bookBox.get());
                            newPosition.setQuantity(1);
                            user.getCart().add(newPosition);
                        });
    }

    @Override
    public void removeBook(final int bookId) {
        final Set<Position> positions = ((User) this.httpSession.getAttribute("user")).getCart();
        new HashSet<>(positions).stream()
                .filter(p -> p.getBook().getId() == bookId)
                .forEach(positions::remove);
    }
}
