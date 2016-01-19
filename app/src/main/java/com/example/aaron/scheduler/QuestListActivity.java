package com.example.aaron.scheduler;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class QuestListActivity extends AppCompatActivity {

    private static final int MENU_ITEM_LOGOUT = 1001;

    private ListView listView;
    private List<Quest> quests;
    private List<String> questNames;
    private QuestListAdapter adapter;
    private Quest selectedQuest = new Quest("");

    private Button addQuestBtn;
    private Button deleteQuestBtn;
    private EditText questNameEditText;
    private EditText idEditText;

    private DataBaseHelper myDb;
    private DataModel dm;

    private static String webUrl = "https://www.facebook.com";
    private static String email = "leeaaron326@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String[] addresses = {email};
//                Intent sendEmail = new Intent(Intent.ACTION_SENDTO);
//                sendEmail.setData(Uri.parse("mailto:"));
//                sendEmail.putExtra(Intent.EXTRA_EMAIL, addresses);
//                sendEmail.putExtra(Intent.EXTRA_SUBJECT, "Information Request");
//                sendEmail.putExtra(Intent.EXTRA_TEXT, "Please send some information!");
//                if (getIntent().resolveActivity(getPackageManager()) != null) {
//                    startActivity(sendEmail);
//                }
            }
        });

        // Initializing data manager and database
        myDb = new DataBaseHelper(this);
        dm = new DataModel();

        questNames = dm.getQuestNames(); // x123: This is currently used to test ListView

        // Initializing EditText
        questNameEditText = (EditText) findViewById(R.id.questNameEditText);
        idEditText = (EditText) findViewById(R.id.IdEditText);

        // Initializing listView
        String[] questList = getResources().getStringArray(R.array.questList_test);
        final ArrayAdapter<String> adapterTest = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1,
                questNames);

//        QuestListAdapter adapter = new QuestListAdapter(
//                this, R.layout.quest_list_layout, quests);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapterTest);

        // Initializing the addQuestBtn and its listener
        addQuestBtn = (Button) findViewById(R.id.addQuestBtn);
        addQuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update DateModel
                boolean isUpdate = dm.addQuestTest(questNameEditText.getText().toString());

                // Notify and update ListView
                if (isUpdate == true) {
                    adapterTest.notifyDataSetChanged(); // x123: Testing listView
                } else {
                    Toast.makeText(QuestListActivity.this, "Error: Data not added", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        // Initializing the deleteQuestBtn and its listener
        deleteQuestBtn = (Button) findViewById(R.id.delete_button);
        deleteQuestBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Update DateModel
                boolean isUpdate = dm.deleteSelectedQuest(selectedQuest); // x123: Currently deleting 0 index of questList

                // Notify and update ListView
                if (isUpdate == true) {
                    adapterTest.notifyDataSetChanged(); // x123: Testing ListView
                } else {
                    Toast.makeText(QuestListActivity.this, "Error: Date not deleted", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }

    public void showAlertMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestListActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quest_list, menu);

        menu.add(0, MENU_ITEM_LOGOUT, 102, R.string.action_logout);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Snackbar.make(getCurrentFocus(), "You selected settings", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            case R.id.action_about:
                Intent goToAbout = new Intent(this, AboutActivity.class);
                startActivity(goToAbout);
                return true;
            case MENU_ITEM_LOGOUT:
                Snackbar.make(getCurrentFocus(), "You selected Logout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            case R.id.action_shopping_cart:
                Snackbar.make(getCurrentFocus(), "You selected Shopping cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            case R.id.action_facebook:
                Intent goFacebook = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                if (goFacebook.resolveActivity(getPackageManager()) != null) {
                    startActivity(goFacebook);
                }
        }

        return super.onOptionsItemSelected(item);
    }
}
