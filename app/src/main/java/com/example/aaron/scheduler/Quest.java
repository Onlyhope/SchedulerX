package com.example.aaron.scheduler;

import java.util.Date;

/**
 * Created by Aaron on 1/15/2016.
 */
public class Quest {

    public static final String TYPE_DEFAULT = "default";
    public static final String DEADLINE_DEFAULT = "00/00/00";

    private int questId;
    private String questName;
    private String questDeadline;
    private String questType;


    public Quest(String name) {
        questId = -1;
        questName = name;
        questType = TYPE_DEFAULT;
        questDeadline = DEADLINE_DEFAULT;
    }

    // Getters

    public int getQuestId() {return questId; }

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

    public void setQuestId(int id) { this.questId = id; }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public void setQuestType(String questType) {
        this.questType = questType;
    }

    public void setQuestDeadline(String questDeadline) {
        this.questDeadline = questDeadline;
    }
}
