package ru.geekbrains.hibernate.m2m;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.geekbrains.hibernate.PrepareDataApp;
import ru.geekbrains.hibernate.m2m.meta.Payer;
import ru.geekbrains.hibernate.m2m.meta.Product;

import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        PrepareDataApp.forcePrepareData();

        SessionFactory factory = new Configuration()
                .configure("configs/hibernate.cfg.xml")
                .buildSessionFactory();

        Session session = null;
        try {
            Scanner in = new Scanner(System.in);
            String command;

            System.out.print("Введите команду ('/quit' - выход): ");

            while (in.hasNextLine()) {
                command = in.nextLine();
                if (command.equals("/quit")) {
                    break;
                } else {
                    String[] parts = command.split(" ");

                    switch (parts[0]) {
                        case "/help":
                            System.out.println("/help - список команд");
                            System.out.println("/payers - Список плательщиков");
                            System.out.println("/payer N - Плательщик N и продукты");
                            System.out.println("/products - Список продуктов");
                            System.out.println("/product N - Продукт N и его покупатели");
                            System.out.println("/del N - Удалить продукт N");
                            System.out.println("/quit - выход");
                            break;
                        case "/payers":
                            session = factory.getCurrentSession();
                            session.beginTransaction();
                            List payers = session.createQuery("SELECT r FROM Payer r ORDER BY r.id").getResultList();
                            System.out.println(payers);
                            session.getTransaction().commit();
                            break;
                        case "/payer":
                            if (parts.length > 1) {
                                session = factory.getCurrentSession();
                                session.beginTransaction();
                                Payer payer = session.get(Payer.class, Long.valueOf(parts[1]));
                                System.out.println(payer);
                                System.out.println("Продукты: ");
                                for (Product b : payer.getProducts()) {
                                    System.out.println(b.getTitle());
                                }
                                session.getTransaction().commit();
                            } else System.out.println("Неверная команда, список команд /help");
                            break;
                        case "/products":
                            session = factory.getCurrentSession();
                            session.beginTransaction();
                            List products = session.createQuery("SELECT r FROM Product r ORDER BY r.id").getResultList();
                            System.out.println(products);
                            session.getTransaction().commit();
                            break;
                        case "/product":
                            if (parts.length > 1) {
                                session = factory.getCurrentSession();
                                session.beginTransaction();
                                Product product = session.get(Product.class, Long.valueOf(parts[1]));
                                System.out.println(product);
                                System.out.println("Покупатели: ");
                                for (Payer b : product.getPayers()) {
                                    System.out.println(b.getName());
                                }
                                session.getTransaction().commit();
                            } else System.out.println("Неверная команда, список команд /help");
                            break;
                        case "/del":
                            if (parts.length > 1) {
                                session = factory.getCurrentSession();
                                session.beginTransaction();

                                List<Payer> allPayers = session.createQuery("SELECT r FROM Payer r ORDER BY r.id").getResultList();
                                for (Payer b : allPayers) {
                                    b.getProducts().removeIf(p -> p.getId().equals(Long.valueOf(parts[1])));
                                }

                                session.getTransaction().commit();
                            } else System.out.println("Неверная команда, список команд /help");
                            break;
                        default:
                            System.out.println("Неверная команда, список команд /help");
                    }
                }
                System.out.print("Введите команду ('/quit' - выход): ");
            }
        } finally {
            factory.close();
            if (session != null) {
                session.close();
            }
        }
    }
}