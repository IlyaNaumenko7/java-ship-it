package ru.yandex.practicum.delivery;

public class FragileParcel extends Parcel implements Trackable {
    private static final double BASE_COST = 4.0;

    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        System.out.println("Посылка <<" + description + ">> обёрнута в защитную плёнку");
        System.out.println("Посылка <<" + description + ">> упакована");
    }

    @Override
    public double calculateDeliveryCost() {
        return weight * BASE_COST;
    }

    @Override
    public void reportStatus(String newLocation) {
        System.out.println("Хрупкая посылка <<" + description + ">> изменила местоположение на " + newLocation);
    }
}