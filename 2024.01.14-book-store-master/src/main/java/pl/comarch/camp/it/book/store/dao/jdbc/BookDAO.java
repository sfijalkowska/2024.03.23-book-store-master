package pl.comarch.camp.it.book.store.dao.jdbc;

import pl.comarch.camp.it.book.store.dao.IBookDAO;
import pl.comarch.camp.it.book.store.dao.IUserDAO;
import pl.comarch.camp.it.book.store.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDAO implements IBookDAO {

    private final Connection connection;
    private final IUserDAO userDAO;

    public BookDAO(Connection connection, IUserDAO userDAO) {
        this.connection = connection;
        this.userDAO = userDAO;
    }

    @Override
    public Optional<Book> getById(int id) {
        try {
            String sql = "SELECT * FROM tbook WHERE id = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return Optional.of(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        this.userDAO.getById(rs.getInt("user_id")).get()));
            }
        } catch (SQLException e) {
            System.out.println("Getting book by id error !!");
        }
        return Optional.empty();
    }

    @Override
    public List<Book> getAll() {
        List<Book> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tbook";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                result.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        this.userDAO.getById(rs.getInt("user_id")).get()));
            }
        } catch (SQLException e) {
            System.out.println("Getting all books error !");
        }
        return result;
    }

    @Override
    public List<Book> getByPattern(String pattern) {
        List<Book> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tbook WHERE title LIKE ? OR author LIKE ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, "%"+pattern+"%");
            preparedStatement.setString(2, "%"+pattern+"%");

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                result.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        this.userDAO.getById(rs.getInt("user_id")).get()));
            }
        } catch (SQLException e) {
            System.out.println("Getting books by pattern error !");
        }
        return result;
    }

    @Override
    public void save(Book book) {
        try {
            String sql = "INSERT INTO tbook (title, author, isbn, price, quantity, user_id) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setDouble(4, book.getPrice().doubleValue());
            preparedStatement.setInt(5, book.getQuantity());
            preparedStatement.setInt(6, book.getCreator().getId());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            book.setId(rs.getInt(1));
        } catch (SQLException e) {
            System.out.println("Saving book error !");
        }
    }

    @Override
    public void update(Book book) {
        try {
            String sql = "UPDATE tbook SET title = ?, author = ?, isbn = ?, price = ?, quantity = ?, user_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setDouble(4, book.getPrice().doubleValue());
            preparedStatement.setInt(5, book.getQuantity());
            preparedStatement.setInt(6, book.getCreator().getId());
            preparedStatement.setInt(7, book.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Updating book error !");
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM tbook WHERE id = ?;";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Removing book error !");
        }
    }
}
