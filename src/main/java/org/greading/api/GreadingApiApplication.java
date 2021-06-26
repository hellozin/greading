package org.greading.api;

import java.util.ArrayList;
import java.util.List;
import org.greading.api.util.DevelopUtils;
import org.greading.api.vote.Vote;
import org.greading.api.vote.VoteService;
import org.greading.api.vote.selection.Selection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class GreadingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreadingApiApplication.class, args);
    }

}

@Component
class Runner implements CommandLineRunner {

    @Autowired
    private VoteService voteService;

    @Override
    public void run(String... args) {
        List<Selection> selections = new ArrayList<>();

        selections.add(new Selection("first selection"));
        selections.add(new Selection("second selection"));
        selections.add(new Selection("third selection"));

        Vote vote = voteService.createVote("first vote", selections);
        System.out.println("vote id : " + vote.getId());

        List<Selection> all = vote.getSelections();
        long lastSelectionId = all.get(all.size() - 1).getId();

        voteService.vote(vote.getId(), lastSelectionId, DevelopUtils.generateMockId());
        voteService.vote(vote.getId(), lastSelectionId, DevelopUtils.generateMockId());
    }
}