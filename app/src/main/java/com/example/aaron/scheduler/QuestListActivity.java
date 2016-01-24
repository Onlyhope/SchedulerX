package com.example.aaron.scheduler;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.List;

public class QuestListActivity extends AppCompatActivity {

    private static final int MENU_ITEM_LOGOUT = 1001;

    private ListView listView;
    private List<Quest> quests;
    private QuestListAdapter adapter;
    private Quest selectedQuest = new Quest("", "");

    private Button addQuestBtn;
    private EditText questNameEditText;

    private DataModel dm;

    private static String webUrl = "https://www.facebook.com";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Log.d("x123", "onCreate");

        // Initializing data manager and database
        dm = new DataModel(this);

        quests = dm.getQuestList();

        // Initializing EditText
        questNameEditText = (EditText) findViewById(R.id.questNameEditText);

        // Initializing listView

        adapter = new QuestListAdapter(
                this, R.layout.quest_list_layout, quests);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        // Initializing Buttons
        addQuestBtn = (Button) findViewById(R.id.addQuestBtn);

        initEventHandlers();
    }

    public void initEventHandlers() {
        //Initializing Event Handlers
        addQuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update DateModel
                if (questNameEditText.getText().toString().equals(""))
                    return;

                boolean isUpdate = dm.addQuest(questNameEditText.getText().toString());

                // Notify and update ListView
                if (isUpdate == true) {
                    adapter.notifyDataSetChanged();
                    questNameEditText.setText("");
                } else {
                    Toast.makeText(QuestListActivity.this, "Error: Data not added", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (selectedQuest.equals(quests.get(position))) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.inflate(R.menu.quest_menu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(QuestListActivity.this, item.getTitle().toString(), Toast.LENGTH_LONG).show();

                            String userClick = item.getTitle().toString();

                            switch (userClick) {
                                case "Edit":
                                    Intent goToEditQuestActivity = new Intent(QuestListActivity.this, AboutActivity.class);
                                    startActivity(goToEditQuestActivity);
                                    break;
                                case "Delete":
                                    boolean isUpdate = dm.deleteSelectedQuest(selectedQuest);
                                    // Notify and update ListView
                                    if (isUpdate == true) {
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(QuestListActivity.this, "Error: Date not deleted", Toast.LENGTH_LONG).show();
                                    }
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();
                    Toast.makeText(QuestListActivity.this, "Item already selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QuestListActivity.this, "New Item Selected", Toast.LENGTH_SHORT).show();
                    selectedQuest = quests.get(position);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.listView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

            String[] menuItems = getResources().getStringArray(R.array.quest_menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }
}
