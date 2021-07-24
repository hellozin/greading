package org.greading.api.util;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.greading.api.member.Member;
import org.greading.api.member.MemberService;
import org.greading.api.request.JoinRequest;
import org.greading.api.vote.Vote;
import org.greading.api.vote.VoteService;
import org.greading.api.vote.selection.Selection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class MockDataInitializer implements CommandLineRunner {

    private final MemberService memberService;

    private final VoteService voteService;

    public MockDataInitializer(MemberService memberService, VoteService voteService) {
        this.memberService = memberService;
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

        final int mockUserCount = 2;
        Member[] mockUsers = new Member[mockUserCount];
        for (int i = 0; i < mockUserCount; i++) {
            JoinRequest joinRequest = new JoinRequest(
                    "id" + i, "pw" + i, "mail" + i, "none");
            mockUsers[i] = memberService.signUp(joinRequest);
        }

        for (int i = 0; i < mockUserCount; i++) {
            vote = voteService.vote(vote.getId(), lastSelectionId, mockUsers[i].getId());
        }

        DevelopUtils.printPretty(vote);
    }

}
