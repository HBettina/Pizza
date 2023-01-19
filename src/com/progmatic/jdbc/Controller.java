package com.progmatic.jdbc;

import com.progmatic.jdbc.dao.*;
import com.progmatic.jdbc.model.*;

import java.util.List;

public class Controller implements AutoCloseable {

    DBEngine engine;
    PizzaDao pizzaDao;
    CourierDao courierDao;
    ClientDao clientDao;
    OrderItemDao orderItemDao;
    OrderDao orderDao;

    public Controller() {
        this.engine = new DBEngine();
        this.pizzaDao = new PizzaDao(this.engine);
        this.courierDao = new CourierDao(this.engine);
        this.clientDao = new ClientDao(this.engine);
        this.orderItemDao = new OrderItemDao(this.engine, this.pizzaDao);
        this.orderDao = new OrderDao(this.engine, courierDao, clientDao, this.orderItemDao);
    }

    public List<Pizza> getAllPizza() {
        return this.pizzaDao.getAll();
    }

    public List<Courier> getAllCourier() {
        return this.courierDao.getAll();
    }

    public List<Client> getAllClient() {
        return this.clientDao.getAll();
    }
    public Client getAClient(long cid) {
        return this.clientDao.get(cid);
    }
    public Courier getACourier(long fazon) {
        return this.courierDao.get(fazon);
    }

    public List<Order> getAllOrder() {
        return this.orderDao.getAll();
    }

    public void addPizza(Pizza pizza){
        this.pizzaDao.save(pizza);
    }
    public void addOrder(Order order){
        this.orderDao.save(order);
    }
    @Override
    public void close() throws Exception {
        if (engine != null && !engine.isClosed()) {
            engine.close();
        }
    }
}
