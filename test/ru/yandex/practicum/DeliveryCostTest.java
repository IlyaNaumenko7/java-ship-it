package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ru.yandex.practicum.delivery.ParcelBox;
import ru.yandex.practicum.delivery.StandardParcel;
import ru.yandex.practicum.delivery.FragileParcel;
import ru.yandex.practicum.delivery.PerishableParcel;
import ru.yandex.practicum.delivery.Parcel;

public class DeliveryCostTest {

    private StandardParcel standard;
    private FragileParcel fragile;
    private PerishableParcel perishable;
    private ParcelBox<StandardParcel> box;

    @BeforeEach
    void setUp() {
        standard = new StandardParcel("Книги", 5, "Москва", 1);
        fragile = new FragileParcel("Ваза", 3, "СПб", 2);
        perishable = new PerishableParcel("Торт", 2, "Казань", 1, 3);
        box = new ParcelBox<>(10);
    }

    // === Тесты стоимости доставки ===
    @Test
    void standardCostCalculation() {
        assertEquals(10.0, standard.calculateDeliveryCost()); // 5 * 2
    }

    @Test
    void fragileCostCalculation() {
        assertEquals(12.0, fragile.calculateDeliveryCost()); // 3 * 4
    }

    @Test
    void perishableCostCalculation() {
        assertEquals(6.0, perishable.calculateDeliveryCost()); // 2 * 3
    }

    // === Тесты isExpired ===
    @Test
    void perishableNotExpired() {
        assertFalse(perishable.isExpired(3)); // 1 + 3 >= 3
    }

    @Test
    void perishableExpired() {
        assertTrue(perishable.isExpired(5)); // 1 + 3 < 5
    }

    @Test
    void perishableBoundaryDay() {
        assertFalse(perishable.isExpired(4)); // 1 + 3 == 4 — ещё не испортилась
    }

    // === Тесты ParcelBox ===
    @Test
    void addParcelWithinLimit() {
        StandardParcel p1 = new StandardParcel("A", 3, "X", 1);
        StandardParcel p2 = new StandardParcel("B", 4, "Y", 1);

        assertTrue(box.addParcel(p1));
        assertTrue(box.addParcel(p2));
        assertEquals(2, box.getAllParcels().size());
    }

    @Test
    void addParcelExceedsLimit() {
        StandardParcel p1 = new StandardParcel("A", 6, "X", 1);
        StandardParcel p2 = new StandardParcel("B", 5, "Y", 1);

        assertTrue(box.addParcel(p1)); // 6 <= 10
        assertFalse(box.addParcel(p2)); // 6 + 5 > 10
        assertEquals(1, box.getAllParcels().size());
    }

    @Test
    void addParcelExactLimit() {
        StandardParcel p1 = new StandardParcel("A", 10, "X", 1);
        assertTrue(box.addParcel(p1)); // ровно 10
        assertEquals(1, box.getAllParcels().size());
    }
}