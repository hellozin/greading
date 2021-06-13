package org.greading.api.util;

import java.util.UUID;

public class DevelopUtils {

    private DevelopUtils() {
    }

    public static long generateMockId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

}
