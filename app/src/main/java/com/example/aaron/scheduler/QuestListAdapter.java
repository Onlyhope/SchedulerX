package com.example.aaron.scheduler;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
        nameText.setText(quest.getQuestName());

//        convertView.setOnLongClickListener(longListen);

//        Button editQuest_Button = (Button) convertView.findViewById(R.id.edit_quest_button);
//        editQuest_Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popup = new PopupMenu(v.getContext(), v);
//                popup.inflate(R.menu.quest_menu);
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                Toast.makeText(getContext(), item.getTitle().toString(), Toast.LENGTH_LONG).show();
//                                return false;
//                            }
//                        });
//                popup.show();
//            }
//        });

        return convertView;
    }
}
