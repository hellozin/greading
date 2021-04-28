package org.greading.api.vote;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.greading.api.vote.model.Selection;
import org.greading.api.vote.model.Vote;
import org.junit.jupiter.api.Test;

class VoteServiceTest {

    @Test
    void test() {
        VoteService voteService = new VoteService();

        Map<Long, Selection> selections = new HashMap<>();
        long id = generateId();
        selections.put(id, new Selection(id, "first selection"));
        id = generateId();
        selections.put(id, new Selection(id, "second selection"));
        id = generateId();
        selections.put(id, new Selection(id, "third selection"));

        long voteId = voteService.createVote("first vote", selections);
        Vote vote = voteService.getVote(voteId);

        long lastSelectionId = selections.get(id).getId();

        long userId = generateId();
        voteService.vote(vote.getId(), lastSelectionId, userId);
        voteService.vote(vote.getId(), lastSelectionId, generateId());

        vote.printPretty();
    }

    private long generateId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

}