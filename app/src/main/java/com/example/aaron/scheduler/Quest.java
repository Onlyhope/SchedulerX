package com.example.aaron.scheduler;

import java.util.Date;

/**
 * Created by Aaron on 1/15/2016.
 */
public class Quest {

    public static final String QUEST_DEFAULT = "default";

    private String questTitle;
    private String questType;
    private Date questDeadline;

    public Quest(String title, String type, Date date) {
        questTitle = title;
        questType = QUEST_DEFAULT;
        questDeadline = date;
    }

    // Getters

    public Date getQuestDeadline() {
        return questDeadline;
    }

    public String getQuestType() {
        return questType;
    }

    public String getQuestTitle() {
        return questTitle;
    }

    // Setters

    public void setQuestTitle(String questTitle) {
        this.questTitle = questTitle;
    }

    public void setQuestType(String questType) {
        this.questType = questType;
    }

    public void setQuestDeadline(Date questDeadline) {
        this.questDeadline = questDeadline;
    }
}
