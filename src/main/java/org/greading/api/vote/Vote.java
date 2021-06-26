package org.greading.api.vote;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.greading.api.vote.selection.Selection;

@Entity
public class Vote {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    @OneToMany
    private List<Selection> selections;

    protected Vote() {
    }

    public Vote(String title, List<Selection> selections) {
        this.title = title;
        this.selections = selections;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Selection getTopSelection() {
        return selections.stream()
                .max(Comparator.comparing(selection ->
                        selection.getSelectUserIds().size()))
                .orElseThrow();
    }

    public Optional<Selection> getSelection(long selectionId) {
        for (Selection selection : selections) {
            if (selection.getId() == selectionId) {
                return Optional.of(selection);
            }
        }
        return Optional.empty();
    }

    public List<Selection> getSelections() {
        return selections;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", selections=" + selections +
                '}';
    }
}
