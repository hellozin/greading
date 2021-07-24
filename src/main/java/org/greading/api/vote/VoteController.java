package org.greading.api.vote;

import java.util.List;
import org.greading.api.util.DevelopUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/vote/list")
    public List<Vote> getVoteList() {
        return voteService.getAllVote();
    }

    @GetMapping("/vote/{voteId}")
    public Vote getVote(@PathVariable long voteId) {
        return voteService.getVote(voteId).orElseThrow();
    }

    @PostMapping("/vote/{voteId}/selection/{selectionId}")
    public Vote vote(@PathVariable long voteId, @PathVariable long selectionId) {
        return voteService.vote(voteId, selectionId, DevelopUtils.generateMockId());
    }


}
