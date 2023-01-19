package com.progmatic.jdbc.dao;

import com.progmatic.jdbc.DBEngine;
import com.progmatic.jdbc.model.OrderItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrderItemDao implements Dao<OrderItem> {

    private final DBEngine engine;
    private final PizzaDao pizzaDao;
    private final OrderDao orderDao;

    public OrderItemDao(DBEngine engine, PizzaDao pizzaDao, OrderDao orderDao) {
        this.engine = engine;
        this.pizzaDao = pizzaDao;
        this.orderDao = orderDao;
    }

    @Override
    public OrderItem get(long id) {
        return null;
    }

    @Override
    public List<OrderItem> getAll() {
        return null;
    }

    public List<OrderItem> getAll(Long orderId) {
        List<OrderItem> all = new LinkedList<>();

        try (
            PreparedStatement s = engine.getConnection().prepareStatement("SELECT * FROM tetel WHERE razon = ?;");
        ) {
            s.setLong(1, orderId);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                all.add(resultToOrderItem(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return all;
    }

    private OrderItem resultToOrderItem(ResultSet rs) throws SQLException {
        return new OrderItem(
                rs.getLong("razon"),
                this.pizzaDao.get(rs.getLong("pazon")),
            rs.getShort("db")
        );
    }

    @Override
    public void save(OrderItem orderItem) {
        try (
                PreparedStatement s = engine.getConnection().prepareStatement("INSERT INTO tetel (razon, pazon, db) VALUES (?,?,?);");
        ){
            s.setLong(1, orderItem.oid());
            s.setLong(2, orderItem.pizza().pid());
            s.setShort(3, orderItem.number());
            s.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(OrderItem orderItem, String[] params) {

    }

    @Override
    public void delete(OrderItem orderItem) {
        try(
                PreparedStatement s = engine.getConnection().prepareStatement("DELETE FROM tetel WHERE razon = ? AND pazon = ?;");
        ){
            s.setLong(1, orderItem.oid());
            s.setLong(2, orderItem.pizza().pid());
            s.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
