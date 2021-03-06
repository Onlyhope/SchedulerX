package com.example.aaron.scheduler;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.DragEvent;
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
    public static final String QUEST_NAME = "QUEST_NAME";
    public static final String QUEST_DEADLINE = "QUEST_DEADLINE";
    public static final String QUEST_TYPE = "QUEST_TYPE";
    public static final String QUEST_ID = "QUEST_ID";

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

        fab.setOnDragListener(DropListener);

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

        // Initializing Buttons
        addQuestBtn = (Button) findViewById(R.id.addQuestBtn);

        initEventHandlers();
    }

    private View.OnDragListener DropListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();

            switch(dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    Toast.makeText(QuestListActivity.this, "Dropped!", Toast.LENGTH_LONG).show();
                    break;
            }

            return true;
        }
    };

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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(QuestListActivity.this, "On Long Click Listener", Toast.LENGTH_LONG).show();

                ClipData data = ClipData.newPlainText("", "");
                DragShadow dragShadow = new DragShadow(view);

                view.startDrag(data, dragShadow, view, 0);
                return false;
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
                                    Intent goToQuestDetailActivity = new Intent(QuestListActivity.this, QuestDetailActivity.class);
                                    
                                    goToQuestDetailActivity.putExtra(QUEST_ID, selectedQuest.getId());
                                    goToQuestDetailActivity.putExtra(QUEST_NAME, selectedQuest.getQuestName());
                                    goToQuestDetailActivity.putExtra(QUEST_DEADLINE, selectedQuest.getQuestDeadline());
                                    goToQuestDetailActivity.putExtra(QUEST_TYPE, selectedQuest.getQuestType());
                                            
                                    startActivity(goToQuestDetailActivity);
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

    private class DragShadow extends View.DragShadowBuilder {

        ColorDrawable greyBox;

        public DragShadow(View view) {
            super(view);
            greyBox = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            greyBox.draw(canvas);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            View v = getView();

            int height = (int) v.getHeight()/2;
            int width = (int) v.getWidth()/2;

            greyBox.setBounds(0, 0, width, height);

            shadowSize.set(width, height);
            shadowTouchPoint.set((int) width / 2, (int) height / 2);
        }
    }
}
