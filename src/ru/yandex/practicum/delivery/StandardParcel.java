package ru.yandex.practicum.delivery;

public class StandardParcel extends Parcel {
    private static final double BASE_COST = 2.0;

    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        System.out.println("Посылка <<" + description + ">> упакована");
    }

    @Override
    public double calculateDeliveryCost() {
        return weight * BASE_COST;
    }
}