package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Parcel> allParcels = new ArrayList<>();
    private static final List<Trackable> trackableParcels = new ArrayList<>();

    private static final ParcelBox<StandardParcel> standardBox = new ParcelBox<>(100);
    private static final ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(50);
    private static final ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(30);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    trackParcels();
                    break;
                case 5:
                    showBoxContents();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n=== Служба доставки ===");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Отследить посылки");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
        System.out.print("Выбор: ");
    }

    private static void addParcel() {
        System.out.println("\nТип посылки: 1 — Стандартная, 2 — Хрупкая, 3 — Скоропортящаяся");
        int type = Integer.parseInt(scanner.nextLine());

        System.out.print("Описание: ");
        String description = scanner.nextLine();
        System.out.print("Вес (кг): ");
        int weight = Integer.parseInt(scanner.nextLine());
        System.out.print("Адрес: ");
        String address = scanner.nextLine();
        System.out.print("День отправки: ");
        int sendDay = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1: {
                StandardParcel parcel = new StandardParcel(description, weight, address, sendDay);
                if (standardBox.addParcel(parcel)) {
                    allParcels.add(parcel);
                    System.out.println("Посылка добавлена!");
                } else {
                    System.out.println("Превышен вес коробки!");
                }
                break;
            }

            case 2: {
                FragileParcel parcel = new FragileParcel(description, weight, address, sendDay);
                if (fragileBox.addParcel(parcel)) {
                    allParcels.add(parcel);
                    trackableParcels.add(parcel);
                    System.out.println("Посылка добавлена!");
                } else {
                    System.out.println("Превышен вес коробки!");
                }
                break;
            }

            case 3: {
                System.out.print("Срок хранения (дней): ");
                int ttl = Integer.parseInt(scanner.nextLine());
                PerishableParcel parcel = new PerishableParcel(description, weight, address, sendDay, ttl);
                if (perishableBox.addParcel(parcel)) {
                    allParcels.add(parcel);
                    System.out.println("Посылка добавлена!");
                } else {
                    System.out.println("Превышен вес коробки!");
                }
                break;
            }

            default:
                System.out.println("Неверный тип посылки");
                return;
        }
    }

    private static void sendParcels() {
        System.out.println("\n=== Отправка посылок ===");
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    private static void calculateCosts() {
        System.out.println("\n=== Стоимость доставки ===");
        double total = 0;
        for (Parcel parcel : allParcels) {
            double cost = parcel.calculateDeliveryCost();
            System.out.println(parcel.getDescription() + ": " + cost + " руб.");
            total += cost;
        }
        System.out.println("Итого: " + total + " руб.");
    }

    private static void trackParcels() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет посылок с трекингом");
            return;
        }
        System.out.print("Новое местоположение: ");
        String location = scanner.nextLine();
        for (Trackable trackable : trackableParcels) {
            trackable.reportStatus(location);
        }
    }

    private static void showBoxContents() {
        System.out.println("\n=== Содержимое коробок ===");
        System.out.println("1 — Стандартные (вес: " + standardBox.getCurrentWeight() + "/100 кг)");
        System.out.println("2 — Хрупкие (вес: " + fragileBox.getCurrentWeight() + "/50 кг)");
        System.out.println("3 — Скоропортящиеся (вес: " + perishableBox.getCurrentWeight() + "/30 кг)");
        System.out.print("Выберите коробку: ");

        int choice = Integer.parseInt(scanner.nextLine());
        List<? extends Parcel> contents = switch (choice) {
            case 1 -> standardBox.getAllParcels();
            case 2 -> fragileBox.getAllParcels();
            case 3 -> perishableBox.getAllParcels();
            default -> {
                System.out.println("Неверный выбор");
                yield new ArrayList<>();
            }
        };

        if (contents.isEmpty()) {
            System.out.println("Коробка пуста");
        } else {
            for (Parcel p : contents) {
                System.out.println("- " + p.getDescription() + " (" + p.getWeight() + " кг)");
            }
        }
    }
}