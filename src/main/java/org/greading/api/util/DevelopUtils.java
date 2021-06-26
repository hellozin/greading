package org.greading.api.util;

import java.util.UUID;
import org.greading.api.vote.Vote;
import org.greading.api.vote.selection.Selection;

public class DevelopUtils {

    private DevelopUtils() {
    }

    public static long generateMockId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    public static void printPretty(Vote vote) {
        System.out.println("title: " + vote.getTitle());
        for (Selection selection : vote.getSelections()) {
            System.out.println(selection.getText() + " : " + selection.getSelectUserIds().size());
        }
    }

}
