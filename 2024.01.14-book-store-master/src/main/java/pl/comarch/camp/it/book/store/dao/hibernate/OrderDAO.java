package pl.comarch.camp.it.book.store.dao.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.comarch.camp.it.book.store.dao.IOrderDAO;
import pl.comarch.camp.it.book.store.model.Order;
import pl.comarch.camp.it.book.store.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAO implements IOrderDAO {

    private final SessionFactory sessionFactory;
    private final String GET_BY_ID_HQL = "FROM pl.comarch.camp.it.book.store.model.Order WHERE id = :id";
    private final String GET_ALL_HQL = "FROM pl.comarch.camp.it.book.store.model.Order";
    private final String GET_BY_USER_ID_HQL = "FROM pl.comarch.camp.it.book.store.model.User WHERE id = :id";

    public OrderDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Order> getById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<Order> query = session.createQuery(GET_BY_ID_HQL, Order.class);
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
    public List<Order> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<Order> query = session.createQuery(GET_ALL_HQL, Order.class);
        List<Order> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public void save(Order order) {
        this.persist(order);
    }

    @Override
    public void update(Order order) {
        this.persist(order);
    }

    @Override
    public void persist(Order order) {
        Session session = this.sessionFactory.openSession();
        try {
            session.refresh(order.getUser());
            order.getUser().getOrders().add(order);
            session.beginTransaction();
            session.merge(order);
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
            session.remove(new Order(id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Order> getByUserId(int userId) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_BY_USER_ID_HQL, User.class);
        query.setParameter("id", userId);
        try {
            User user = query.getSingleResult();
            return new ArrayList(user.getOrders());
        } catch (NoResultException e) {
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }
}
