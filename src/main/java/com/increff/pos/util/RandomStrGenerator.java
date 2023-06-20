package com.increff.pos.util;

import java.util.UUID;

public class RandomStrGenerator {
    public static String usingRandomUUID() {

        UUID randomUUID = UUID.randomUUID();

        return randomUUID.toString().replaceAll("_", "");

    }

}