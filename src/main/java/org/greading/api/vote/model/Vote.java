package org.greading.api.vote.model;

import java.util.Map;

public class Vote {

    private long id;

    private String title;

    private Map<Long, Selection> selections;

    public Vote(long id, String title, Map<Long, Selection> selections) {
        this.id = id;
        this.title = title;
        this.selections = selections;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Selection getSelection(long selectionId) {
        return selections.get(selectionId);
    }

    public Map<Long, Selection> getSelections() {
        return selections;
    }

    public void printPretty() {
        System.out.println("title: " + this.title);
        for (Selection selection : selections.values()) {
            System.out.println(selection.getText() + " : " + selection.getSelectUserIds().size());
        }
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
