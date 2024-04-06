package pl.comarch.camp.it.book.store.dao.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.comarch.camp.it.book.store.dao.IBookDAO;
import pl.comarch.camp.it.book.store.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDAO implements IBookDAO {

    private final SessionFactory sessionFactory;

    private final String GET_BY_ID_HQL = "FROM pl.comarch.camp.it.book.store.model.Book WHERE id = :id";
    private final String GET_ALL_HQL = "FROM pl.comarch.camp.it.book.store.model.Book";
    private final String GET_BY_PATTERN_HQL = "FROM pl.comarch.camp.it.book.store.model.Book WHERE title LIKE :pattern OR author LIKE :pattern";

    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Book> getById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<Book> query = session.createQuery(GET_BY_ID_HQL, Book.class);
        query.setParameter("id", id);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Book> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<Book> query = session.createQuery(GET_ALL_HQL, Book.class);
        List<Book> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public List<Book> getByPattern(String pattern) {
        Session session = this.sessionFactory.openSession();
        Query<Book> query = session.createQuery(GET_BY_PATTERN_HQL, Book.class);
        query.setParameter("pattern", "%"+pattern+"%");
        List<Book> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public void save(Book book) {
        persist(book);
    }

    @Override
    public void update(Book book) {
        persist(book);
    }

    @Override
    public void persist(Book book) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            Book newBook = session.merge(book);
            book.setId(newBook.getId());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(new Book(id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
