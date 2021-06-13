package org.greading.api.vote;

import java.util.List;
import org.greading.api.util.DevelopUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/vote/list")
    public String getVoteList(Model model) {
        List<Vote> voteList = voteService.getAllVote();
        model.addAttribute("voteList", voteList);
        return "vote_list";
    }

    @GetMapping("/vote/{voteId}")
    public String getVote(@PathVariable long voteId, Model model) {
        Vote vote = voteService.getVote(voteId).orElseThrow();
        model.addAttribute("vote", vote);
        return "vote";
    }

    @PostMapping("/select")
    public String vote(@RequestParam long voteId, @RequestParam long selectionId) {
        voteService.vote(voteId, selectionId, DevelopUtils.generateMockId());
        return "redirect:/vote/" + voteId;
    }


}
