package com.example.aaron.scheduler;

import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 1/18/2016.
 */
public final class DataModel {

    public static List<Quest> questList = new ArrayList<>();
    public static Map<Integer, Quest> questMap = new HashMap<>();

    /**
     * Class Constructor:
     * Upon creation of the data manager, data will be loaded from a SQLiteDatabase
     */
    public DataModel() {
        loadDataList();
    }

    // Getters
    public static List<Quest> getQuestList() {
        return questList;
    }

    public List<String> getQuestNames() {
        List<String> questNames = new ArrayList<>();
        for (Quest quest : questList) {
            questNames.add(quest.getQuestName());
        }
        return questNames;
    }

    public static List<Quest> getFilteredList(int searchId) {

        List<Quest> filteredList = new ArrayList<>();
        for (Quest quest : questList) {
            if (quest.getQuestId() == searchId) {
                filteredList.add(quest);
            }
        }
        return filteredList;
    }

    // Class Primary Methods
    /**
     * Loads the data from the SQLiteDatabase
     */
    private static void loadDataList() {
        // Retrieve data from SQLiteDataBase
    }

    /**
     * Saves the date to the SQLiteDatabase
     */
    public static void savaDataList() {

        /*
        // Add Data
        boolean isInserted = myDb.insertData(questNameEditText.getText().toString(), Quest.DEADLINE_DEFAULT, Quest.TYPE_DEFAULT);
        if (isInserted) {
            Toast.makeText(QuestListActivity.this, "Data inserted", Toast.LENGTH_LONG).show();

            //Updating listView
            boolean isUpdate = myDb.updateData(idEditText.getText().toString(),
                    questNameEditText.getText().toString(),
                    Quest.DEADLINE_DEFAULT,
                    Quest.TYPE_DEFAULT);

            if (isUpdate) {

            }

            Cursor result = myDb.getAllData();
            if (result.getCount() == 0) {
                //No data was retrieved
                showMessage("Error", "Nothing found");
                return;
            }

            StringBuffer buffer = new StringBuffer();
            while (result.moveToNext()) {
                buffer.append("Id : " + result.getString(0) + "\n");
                buffer.append("Name : " + result.getString(1) + "\n");
                buffer.append("Deadline : " + result.getString(2) + "\n");
                buffer.append("Type : " + result.getString(3) + "\n\n");
            }
            // Show all data
            showMessage("Data", buffer.toString());
        } else {
            Toast.makeText(QuestListActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
        }

        // Delete Data
        Integer deletedRows = myDb.deleteData(idEditText.getText().toString());

                if (deletedRows > 0) {
                    Toast.makeText(QuestListActivity.this, deletedRows + " rows deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QuestListActivity.this, "No data deleted", Toast.LENGTH_SHORT).show();
                }
        */
    }

    /**
     * Creates a new quest and adds it to the questList and questMap.
     * @param name The name of the new Quest.
     * @param itemId The Id of the new Quest.
     * @return true if quest is successfully added.
     */
    public boolean addQuest(String name, int itemId) {
        Quest quest = new Quest(name);
        questList.add(quest);
        questMap.put(itemId, quest);

        return true;
    }

    public boolean addQuestTest(String name) {
        Quest quest = new Quest(name);
        questList.add(quest);

        return true;
    }

    public boolean deleteSelectedQuest(Quest deleteQuest) {
        questList.remove(0);

        return true;
    }
}
