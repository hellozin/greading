package org.greading.api.vote.model;

import java.util.HashSet;
import java.util.Set;

public class Selection {

    private long id;

    private String text;

    private Set<Long> selectUserIds;

    public Selection(long id, String text) {
        this.id = id;
        this.text = text;
        this.selectUserIds = new HashSet<>();
    }

    public Selection(long id, String text, Set<Long> selectUserIds) {
        this.id = id;
        this.text = text;
        this.selectUserIds = selectUserIds;
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
