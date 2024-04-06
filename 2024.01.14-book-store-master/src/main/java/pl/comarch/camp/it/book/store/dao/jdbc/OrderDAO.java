package pl.comarch.camp.it.book.store.dao.jdbc;

import pl.comarch.camp.it.book.store.dao.IOrderDAO;
import pl.comarch.camp.it.book.store.dao.IPositionDAO;
import pl.comarch.camp.it.book.store.dao.IUserDAO;
import pl.comarch.camp.it.book.store.model.Order;
import pl.comarch.camp.it.book.store.model.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAO implements IOrderDAO {

    private final Connection connection;
    private final IUserDAO userDAO;
    private final IPositionDAO positionDAO;

    public OrderDAO(Connection connection, IUserDAO userDAO, IPositionDAO positionDAO) {
        this.connection = connection;
        this.userDAO = userDAO;
        this.positionDAO = positionDAO;
    }

    @Override
    public Optional<Order> getById(int id) {
        try {
            String sql = "SELECT * FROM torder WHERE id = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(Order.Status.valueOf(rs.getString("stuts")));
                order.setDate(rs.getTimestamp("date").toLocalDateTime());
                order.setUser(this.userDAO.getById(rs.getInt("user_id")).get());
                order.getPositions().addAll(this.positionDAO.getByOrderId(order.getId()));
                return Optional.of(order);
            }
        } catch (SQLException e) {
            System.out.println("Getting order by id error !");
        }
        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {
        List<Order> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM torder";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(Order.Status.valueOf(rs.getString("stuts")));
                order.setDate(rs.getTimestamp("date").toLocalDateTime());
                order.setUser(this.userDAO.getById(rs.getInt("user_id")).get());
                order.getPositions().addAll(this.positionDAO.getByOrderId(order.getId()));
                result.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Getting order by id error !");
        }
        return result;
    }

    @Override
    public void save(Order order) {
        try {
            String sql = "INSERT INTO torder (status, total, user_id) VALUES (?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, order.getStatus().name());
            preparedStatement.setDouble(2, order.getTotal());
            preparedStatement.setInt(3, order.getUser().getId());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            order.setId(rs.getInt(1));

            String dateSql = "SELECT date FROM torder where id = ?";
            PreparedStatement datePreparedStatement = connection.prepareStatement(dateSql);
            datePreparedStatement.setInt(1, order.getId());

            ResultSet dateResultSet = datePreparedStatement.executeQuery();
            dateResultSet.next();
            order.setDate(dateResultSet.getTimestamp("date").toLocalDateTime());

            for(Position position : order.getPositions()) {
                this.positionDAO.save(position, order.getId());
            }
        } catch (SQLException e) {
            System.out.println("Saving order error !");
        }
    }

    @Override
    public void update(Order order) {
        try {
            String sql = "UPDATE torder SET date = ?, status = ?, total = ?, user_id = ? WHERE id = ?;";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(order.getDate()));
            preparedStatement.setString(2, order.getStatus().name());
            preparedStatement.setDouble(3, order.getTotal());
            preparedStatement.setInt(4, order.getUser().getId());
            preparedStatement.setInt(5, order.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Updating order error !");
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM torder WHERE id = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            List<Position> positions = this.positionDAO.getByOrderId(id);
            for(Position position : positions) {
                this.positionDAO.delete(position.getId());
            }
        } catch (SQLException e) {
            System.out.println("Removing order error !");
        }
    }

    @Override
    public List<Order> getByUserId(int userId) {
        List<Order> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM torder WHERE user_id = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(Order.Status.valueOf(rs.getString("status")));
                order.setDate(rs.getTimestamp("date").toLocalDateTime());
                order.setUser(this.userDAO.getById(rs.getInt("user_id")).get());
                order.getPositions().addAll(this.positionDAO.getByOrderId(order.getId()));
                result.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Getting orders by user id error !");
        }
        return result;
    }
}
