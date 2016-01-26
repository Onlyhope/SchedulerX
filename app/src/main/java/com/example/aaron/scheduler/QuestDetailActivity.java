package com.example.aaron.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class QuestDetailActivity extends AppCompatActivity {

    private TextView questNameTextView;
    private TextView questDeadlineTextView;
    private TextView questTypeTextView;

    private EditText questNameEditText;
    private EditText questDeadlineEditText;
    private EditText questTypeEditText;

    private Button doneEditButton;

    private DataBaseHelper dbh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initializing DataBaseHelper
        dbh = new DataBaseHelper(this);

        // Getting information passed through intent.
        final String id = getIntent().getStringExtra(QuestListActivity.QUEST_ID);
        String questName = getIntent().getStringExtra(QuestListActivity.QUEST_NAME);
        String questDeadline = getIntent().getStringExtra(QuestListActivity.QUEST_DEADLINE);
        String questType = getIntent().getStringExtra(QuestListActivity.QUEST_TYPE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initializing TextViews and EditText
        questNameTextView = (TextView) findViewById(R.id.quest_name_label);
        questDeadlineTextView = (TextView) findViewById(R.id.quest_deadline_label);
        questTypeTextView = (TextView) findViewById(R.id.quest_type_label);

        questNameEditText = (EditText) findViewById(R.id.edit_name_editText);
        questDeadlineEditText = (EditText) findViewById(R.id.edit_deadline_editText);
        questTypeEditText = (EditText) findViewById(R.id.edit_type_editText);

        // Setting Edit Texts to Quest's information
        questNameEditText.setText(questName);
        questDeadlineEditText.setText(questDeadline);
        questTypeEditText.setText(questType);

        // Initializing Done Edit Button and its Listener
        doneEditButton = (Button) findViewById(R.id.edit_done_button);
        doneEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goQuestListActivity = new Intent(QuestDetailActivity.this, QuestListActivity.class);
                dbh.updateData(id, questNameEditText.getText().toString(),
                        questDeadlineEditText.getText().toString(),
                        questTypeEditText.getText().toString());
                startActivity(goQuestListActivity);
            }
        });
    }

}
