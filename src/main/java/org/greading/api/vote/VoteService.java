package org.greading.api.vote;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.greading.api.vote.model.Selection;
import org.greading.api.vote.model.Vote;

public class VoteService {

    private Map<Long, Vote> votes = new HashMap<>();

    public long createVote(String title, Map<Long, Selection> selections) {
        Vote vote = new Vote(generateId(), title, selections);
        return this.saveVote(vote);
    }

    public long saveVote(Vote vote) {
        votes.put(vote.getId(), vote);
        return vote.getId();
    }

    public void vote(long voteId, long selectionId, long userId) {
        votes.get(voteId).getSelection(selectionId).select(userId);
    }

    public Vote getVote(long voteId) {
        return votes.get(voteId);
    }

    private long generateId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
