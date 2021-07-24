package org.greading.api.util;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.greading.api.vote.Vote;
import org.greading.api.vote.VoteService;
import org.greading.api.vote.selection.Selection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MockDataInitializer implements CommandLineRunner {

    private final VoteService voteService;

    public MockDataInitializer(VoteService voteService) {
        this.voteService = voteService;
    }

    @Transactional
    @Override
    public void run(String... args) {
        List<Selection> selections = new ArrayList<>();

        selections.add(new Selection("first selection"));
        selections.add(new Selection("second selection"));
        selections.add(new Selection("third selection"));

        Vote vote = voteService.createVote("first vote", selections);

        List<Selection> all = vote.getSelections();
        long lastSelectionId = all.get(all.size() - 1).getId();

        vote = voteService.vote(vote.getId(), lastSelectionId, DevelopUtils.generateMockId());
        vote = voteService.vote(vote.getId(), lastSelectionId, DevelopUtils.generateMockId());

        DevelopUtils.printPretty(vote);
    }

}
