package org.example.courier;

import org.example.model.Courier;

import static org.example.utils.Utils.randomString;

public class CourierGenerator {
    public static Courier randomCourier() {
        return new Courier()
                .withLogin(randomString(8))
                .withPassword(randomString(4))
                .withFirstName(randomString(10));
    }

    public static Courier courierWithoutLogin() {
        return new Courier()
                .withPassword(randomString(4))
                .withFirstName(randomString(10));
    }

    public static Courier courierWithoutPassword() {
        return new Courier()
                .withLogin(randomString(8))
                .withFirstName(randomString(10));
    }
}
