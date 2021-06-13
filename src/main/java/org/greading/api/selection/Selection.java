package org.greading.api.selection;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Selection {

    @Id
    @GeneratedValue
    private long id;

    private String text;

    @ElementCollection
    private Set<Long> selectUserIds;

    protected Selection() {
    }

    public Selection(String text) {
        this.text = text;
        this.selectUserIds = new HashSet<>();
    }

    public void select(long userId) {
        selectUserIds.add(userId);
    }

    public void unselect(long userId) {
        selectUserIds.remove(userId);
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Set<Long> getSelectUserIds() {
        return selectUserIds;
    }

    @Override
    public String toString() {
        return "Selection{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", selectUserIds=" + selectUserIds +
                '}';
    }
}
