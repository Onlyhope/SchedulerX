package com.example.aaron.scheduler;

import java.util.Date;

/**
 * Created by Aaron on 1/15/2016.
 */
public class Quest {

    public static final String QUEST_DEFAULT = "default";
    public static final String DEADLINE_DEFAULT = "00/00/00";

    private String questTitle;
    private String questDeadline;
    private String questType;


    public Quest(String title) {
        questTitle = title;
        questType = QUEST_DEFAULT;
        questDeadline = DEADLINE_DEFAULT;
    }

    // Getters

    public String getQuestDeadline() {
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

    public void setQuestDeadline(String questDeadline) {
        this.questDeadline = questDeadline;
    }
}
