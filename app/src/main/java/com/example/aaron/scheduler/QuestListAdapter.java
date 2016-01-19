package com.example.aaron.scheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aaron on 1/18/2016.
 */
public class QuestListAdapter extends ArrayAdapter<Quest> {

    private List<Quest> quests;

    public QuestListAdapter(Context context, int resource, List<Quest> objects) {
        super(context, resource, objects);

        quests = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.quest_list_layout, parent, false);
        }

        Quest quest = quests.get(position);

        TextView nameText = (TextView) convertView.findViewById(R.id.quest_TextView);
        nameText.setText(quest.getQuestTitle());

        return super.getView(position, convertView, parent);
    }
}
