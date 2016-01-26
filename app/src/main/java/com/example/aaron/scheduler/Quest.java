package com.example.aaron.scheduler;

import java.util.Date;

/**
 * Created by Aaron on 1/15/2016.
 */
public class Quest {

    public static final String TYPE_DEFAULT = "default";
    public static final String DEADLINE_DEFAULT = "00/00/00";

    private String id;
    private String questName;
    private String questDeadline;
    private String questType;

    public Quest(String id, String name) {
        this.id = id;
        questName = name;
        questDeadline = DEADLINE_DEFAULT;
        questType = TYPE_DEFAULT;
    }

    public Quest(String id, String name, String deadline, String type) {
        this.id = id;
        questName = name;
        questDeadline = deadline;
        questType = type;
    }

    // Getters

    public String getId() {return id; }

    public String getQuestDeadline() {
        return questDeadline;
    }

    public String getQuestType() {
        return questType;
    }

    public String getQuestName() {
        return questName;
    }

    // Setters

    public void setId(String id) { this.id = id; }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public void setQuestType(String questType) {
        this.questType = questType;
    }

    public void setQuestDeadline(String questDeadline) {
        this.questDeadline = questDeadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quest quest = (Quest) o;

        if (id != null ? !id.equals(quest.id) : quest.id != null) return false;
        return !(questName != null ? !questName.equals(quest.questName) : quest.questName != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (questName != null ? questName.hashCode() : 0);
        return result;
    }
}
