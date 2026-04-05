package ru.yandex.practicum.delivery;

public class PerishableParcel extends Parcel {
    private static final double BASE_COST = 3.0;
    private int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    @Override
    public void packageItem() {
        System.out.println("Посылка <<" + description + ">> упакована");
    }

    @Override
    public double calculateDeliveryCost() {
        return weight * BASE_COST;
    }

    public boolean isExpired(int currentDay) {
        return (sendDay + timeToLive) < currentDay;
    }

    public int getTimeToLive() {
        return timeToLive;
    }
}