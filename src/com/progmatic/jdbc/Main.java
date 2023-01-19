package com.progmatic.jdbc;

import com.progmatic.jdbc.dao.ClientDao;
import com.progmatic.jdbc.model.Client;
import com.progmatic.jdbc.model.Courier;
import com.progmatic.jdbc.model.Order;
import com.progmatic.jdbc.model.Pizza;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    private void printMenu() {
        System.out.println("(U)j rendeles");
        System.out.println("Kere(s)es");
        System.out.println("Ve(v)ok listaja");
        System.out.println("Fu(t)arok listaja");
        System.out.println("Piz(z)ak listaja");
        System.out.println("Ren(d)elesek listaja");
        System.out.println("(K)ilepes");
        System.out.println("Uj (p)izza hozzáadása");
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
                        printAllClient();
                        System.out.println("Add meg a vevő azonosítóját!");
                        Long cid = Long.parseLong(sc.nextLine());
                        Client ujRendelesClient = controll.getAClient(cid);
                        printAllCourier();
                        System.out.println("Add meg a futár azonosítóját!");
                        Long fazon = Long.parseLong(sc.nextLine());
                        Courier newOrderCourier = controll.getACourier(fazon);
                        printAllPizza();
                        System.out.println("Add meg a választott pizza azonosítóját!");
                        Long newOrderPizzaId = Long.parseLong(sc.nextLine());

                        LocalDateTime newOrderDate = LocalDateTime.now();
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
//                        Long ujPizzaSorsza2 = Long.valueOf(sc.nextInt());
                        sc.nextLine();
                        System.out.println("Add meg az új pizza nevét!");
                        String ujPizzaNev = sc.nextLine();
                        System.out.println("Add meg az árát!");
                        Integer ujPizzaAr = sc.nextInt();
                        sc.nextLine();
                        Pizza ujPizza = new Pizza(ujPizzaSorsza, ujPizzaNev, ujPizzaAr);
                        controll.addPizza(ujPizza);
//                        controll.pizzaDao.save(ujPizza);
                    }
                    default -> System.out.println("Ilyen menuelem nincs, kerem valasszon ujra.\n");
                }
                this.printMenu();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
