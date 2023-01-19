package com.progmatic.jdbc.dao;

import com.progmatic.jdbc.DBEngine;
import com.progmatic.jdbc.model.Order;
import com.progmatic.jdbc.model.OrderItem;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderDao implements Dao<Order> {

    DBEngine engine;
    CourierDao courierDao;
    ClientDao clientDao;

    OrderItemDao orderItemDao;

    public OrderDao(DBEngine engine, CourierDao courierDao, ClientDao clientDao, OrderItemDao orderItemDao) {
        this.engine = engine;
        this.clientDao = clientDao;
        this.courierDao = courierDao;
        this.orderItemDao = orderItemDao;
    }


    @Override
    public Order get(long id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        List<Order> all = new LinkedList<>();

        try (
                Statement s = engine.getConnection().createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM rendeles;");
        ) {
            while (rs.next()) {
                all.add(resultToOrder(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return all;
    }

    private Order resultToOrder(ResultSet rs) throws SQLException {
        return new Order(
            rs.getLong("razon"),
            clientDao.get(rs.getLong("vazon")),
            courierDao.get(rs.getLong("fazon")),
            orderItemDao.getAll(rs.getLong("razon")),
            rs.getTimestamp("idopont").toLocalDateTime()
        );
    }

    @Override
    public void save(Order order) {
        try (
                PreparedStatement s = engine.getConnection().prepareStatement("INSERT INTO rendeles VALUES (?,?,?,?);");
        ){
            for (OrderItem item : order.items()) {
                saveOrderItem(item, order.oid());
            }
            System.out.println(order.items());
            s.setLong(1,order.oid());
            s.setLong(2,order.client().cid());
            s.setLong(3,order.courier().cid());
            s.setTimestamp(4, Timestamp.valueOf(order.orderedAt()));
            s.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void saveOrderItem(OrderItem item, Long oid){
        try (
                PreparedStatement s = engine.getConnection().prepareStatement("INSERT INTO tetel (razon, pazon, db) VALUES (?,?,?);");
        ){
            s.setLong(1,oid);
            s.setLong(2,item.pizza().pid());
            s.setShort(3,item.number());
            s.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void update(Order order, String[] params) {

    }

    @Override
    public void delete(Order order) {
        try(
                PreparedStatement s = engine.getConnection().prepareStatement("DELETE FROM rendeles WHERE razon = ?;");
        ){
            s.setLong(1, order.oid());
            s.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
