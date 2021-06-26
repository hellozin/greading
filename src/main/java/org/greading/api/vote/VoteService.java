package org.greading.api.vote;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.greading.api.vote.selection.Selection;
import org.greading.api.vote.selection.SelectionRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    private final SelectionRepository selectionRepository;

    public VoteService(VoteRepository voteRepository, SelectionRepository selectionRepository) {
        this.voteRepository = voteRepository;
        this.selectionRepository = selectionRepository;
    }

    @Transactional
    public Vote createVote(String title, List<Selection> selections) {
        List<Selection> savedSelections = selectionRepository.saveAll(selections);
        return voteRepository.save(new Vote(title, savedSelections));
    }

    @Transactional
    public Vote vote(long voteId, long selectionId, long userId) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow();

        vote.getSelection(selectionId)
                .orElseThrow()
                .select(userId);
        return vote;
    }

    public List<Vote> getAllVote() {
        return voteRepository.findAll();
    }

    public Optional<Vote> getVote(long voteId) {
        return voteRepository.findById(voteId);
    }
}
