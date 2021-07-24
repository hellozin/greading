package org.greading.api.vote;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.greading.api.member.Member;
import org.greading.api.member.MemberService;
import org.greading.api.request.JoinRequest;
import org.greading.api.util.DevelopUtils;
import org.greading.api.vote.selection.Selection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class VoteServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private VoteService voteService;

    @Test
    void test() {
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

        vote = voteService.vote(vote.getId(), lastSelectionId, mockUsers[0].getId());
        int lastSelectionCount = vote.getSelection(lastSelectionId).orElseThrow().getSelectUserIds().size();
        assertEquals(1, lastSelectionCount);

        vote = voteService.vote(vote.getId(), lastSelectionId, mockUsers[1].getId());
        lastSelectionCount = vote.getSelection(lastSelectionId).orElseThrow().getSelectUserIds().size();
        assertEquals(2, lastSelectionCount);

        DevelopUtils.printPretty(vote);
    }

}