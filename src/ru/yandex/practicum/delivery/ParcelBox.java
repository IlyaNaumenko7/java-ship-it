package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private List<T> parcels;
    private int maxWeight;

    public ParcelBox(int maxWeight) {
        this.parcels = new ArrayList<>();
        this.maxWeight = maxWeight;
    }

    public boolean addParcel(T parcel) {
        int currentWeight = parcels.stream().mapToInt(Parcel::getWeight).sum();
        if (currentWeight + parcel.getWeight() > maxWeight) {
            System.out.println("Предупреждение: превышен максимальный вес коробки!");
            return false;
        }
        parcels.add(parcel);
        return true;
    }

    public List<T> getAllParcels() {
        return new ArrayList<>(parcels);
    }

    public int getCurrentWeight() {
        return parcels.stream().mapToInt(Parcel::getWeight).sum();
    }
}