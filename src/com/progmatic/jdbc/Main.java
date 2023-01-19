package com.progmatic.jdbc;

import com.progmatic.jdbc.dao.ClientDao;
import com.progmatic.jdbc.model.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    private void printMenu() {
        System.out.println("(U)j rendeles");
        System.out.println("Kere(s)es");
        System.out.println("Ve(v)ok listaja");
        System.out.println("Fu(t)arok listaja");
        System.out.println("Piz(z)ak listaja");
        System.out.println("Ren(d)elesek listaja");
        System.out.println("Uj (p)izza hozzáadása");
        System.out.println();
        System.out.println("\n(K)ilepes");

    }

    public void start() {
        try (
            Scanner sc = new Scanner(System.in);
            Controller controll = new Controller()
        ) {
            System.out.println("*".repeat(30));
            System.out.println("*" + StringUtils.center("Pizza Prog", 28) + "*");
            System.out.println("*".repeat(30) + "\n");

            String s;
            this.printMenu();
            while (!(s = sc.nextLine()).equalsIgnoreCase("k")) {
                switch (s.toLowerCase()) {
                    case "u" -> {
                        orderMenu(controll, sc);
                    }
                    case "s" ->
                        System.out.println("kereses");
//                        this.startSearch(engine);
                    case "v" -> {
                        printAllClient();
                    }
                    case "d" -> {
                        List<Order> allO = controll.getAllOrder();
                        for (Order o : allO) {
                            System.out.println(o);
                        }
                        System.out.println("\n");
                    }
                    case "t" -> {
                        printAllCourier();
                    }
                    case "z" -> {
                        printAllPizza();
                    }
                    case "p" -> {
                        System.out.println("Add meg az új pizza sorszámát!");
                        Long ujPizzaSorsza = sc.nextLong();
                        sc.nextLine();
                        System.out.println("Add meg az új pizza nevét!");
                        String ujPizzaNev = sc.nextLine();
                        System.out.println("Add meg az árát!");
                        Integer ujPizzaAr = sc.nextInt();
                        sc.nextLine();
                        Pizza ujPizza = new Pizza(ujPizzaSorsza, ujPizzaNev, ujPizzaAr);
                        controll.addPizza(ujPizza);
                    }
                    default -> System.out.println("Ilyen menuelem nincs, kerem valasszon ujra.\n");
                }
                this.printMenu();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void orderMenu(Controller controller, Scanner scanner) {
        printAllClient();
        System.out.println("\n\nWhich Client is order now?(Please answer with cid)");
        Client client;
        while (true) {
            try {
                client = controller.clientDao.get(scanner.nextLong());
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Not valid cid");
            }
        }
        printAllCourier();
        System.out.println("\n Which Courier is order now?(Please answer with cid)");
        Courier courier;
        while (true) {
            try {
                courier = controller.courierDao.get(scanner.nextLong());
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Not valid cid");
            }
        }
        printAllPizza();
        System.out.println("\n Which pizzas add to order?(Please select pid)");
        List<OrderItem> order = new ArrayList<>();
        boolean newOrder = true;
        while (newOrder) {
            System.out.println("Select pid");
            long pid = scanner.nextLong();
            scanner.nextLine();
            System.out.println("How many from this pizza?");
            short count = scanner.nextShort();
            scanner.nextLine();
            System.out.println("Want you continue?(\"exit\" to close any other to continue)");
            if (scanner.nextLine().equalsIgnoreCase("EXIT")) {
                newOrder = false;
            }
            order.add(new OrderItem(controller.pizzaDao.get(pid),count));
        }
        controller.orderDao.save(new Order((long)controller.orderDao.getAll().size() + 1,client,courier,order, LocalDateTime.now()));
    }
    public void printAllClient(){
        try (
                Controller controll = new Controller()
        ) {
            List<Client> allC = controll.getAllClient();
            for (Client c : allC) {
                System.out.println(c);
            }
            System.out.println("\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void printAllCourier(){
        try (
                Controller controll = new Controller()
        ) {
            List<Courier> allC = controll.getAllCourier();
            for (Courier c : allC) {
                System.out.println(c);
            }
            System.out.println("\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void printAllPizza(){
        try (
                Controller controll = new Controller()
        ) {
            List<Pizza> allP = controll.getAllPizza();
            for (Pizza p : allP) {
                System.out.println(p);
            }
            System.out.println("\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        Main m = new Main();
        m.start();
    }
}
