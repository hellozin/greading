package org.greading.api.vote;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.greading.api.vote.selection.Selection;
import org.greading.api.vote.selection.SelectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private Logger logger = LoggerFactory.getLogger(VoteService.class);

    private final VoteRepository voteRepository;

    private final SelectionRepository selectionRepository;

    public VoteService(VoteRepository voteRepository, SelectionRepository selectionRepository) {
        this.voteRepository = voteRepository;
        this.selectionRepository = selectionRepository;
    }

    @Transactional
    public Vote createVote(String title, List<Selection> selections) {
        List<Selection> savedSelections = selectionRepository.saveAll(selections);
        Vote savedVote = voteRepository.save(new Vote(title, savedSelections));

        logger.debug("Vote created. id : {}", savedVote.getId());

        return savedVote;
    }

    @Transactional
    public Vote vote(long voteId, long selectionId, long userId) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow();

        vote.getSelection(selectionId)
                .orElseThrow()
                .select(userId);

        logger.debug("User(id: {}) voted. vote id : {}, selection id {}", userId, voteId, selectionId);

        return vote;
    }

    public List<Vote> getAllVote() {
        return voteRepository.findAll();
    }

    public Optional<Vote> getVote(long voteId) {
        return voteRepository.findById(voteId);
    }
}
