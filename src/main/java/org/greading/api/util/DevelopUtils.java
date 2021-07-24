package org.greading.api.util;

import java.util.UUID;
import org.greading.api.vote.Vote;
import org.greading.api.vote.selection.Selection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DevelopUtils {

    private static Logger logger = LoggerFactory.getLogger(DevelopUtils.class);

    private static long MAX_MOCK_USER_ID = 1000L;

    private DevelopUtils() {
    }

    public static long generateMockId() {
        return UUID.randomUUID().getMostSignificantBits() & MAX_MOCK_USER_ID;
    }

    public static void printPretty(Vote vote) {
        logger.info("===== Vote (id: {}) =====", vote.getId());
        logger.info("title: {}", vote.getTitle());
        for (Selection selection : vote.getSelections()) {
            logger.info("{}: {}", selection.getText(), selection.getSelectUserIds().size());
        }
    }

}
