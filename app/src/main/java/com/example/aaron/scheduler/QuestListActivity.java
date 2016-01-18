package com.example.aaron.scheduler;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestListActivity extends AppCompatActivity {

    private static final int MENU_ITEM_LOGOUT = 1001;
    private static final String DEADLINE_DEFAULT = "00/00/00";
    private static final String TYPE_DEFAULT = "default";
    private ListView listView;

    private Button addQuestBtn;
    private Button deleteQuestBtn;
    private EditText questNameEditText;
    private EditText idEditText;

    private DataBaseHelper myDb;

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
                Toast.makeText(QuestListActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        // Initializing database
        myDb = new DataBaseHelper(this);

        // Initializing EditText
        questNameEditText = (EditText) findViewById(R.id.questNameEditText);
        idEditText = (EditText) findViewById(R.id.IdEditText);

        // Initializing listView
        ArrayList<Quest> questArrayList = new ArrayList<>();
        String[] questList = getResources().getStringArray(R.array.questList_test);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1,
                questList);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Initializing the addQuestBtn and its listener
        addQuestBtn = (Button) findViewById(R.id.addQuestBtn);
        addQuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(questNameEditText.getText().toString(), DEADLINE_DEFAULT, TYPE_DEFAULT);
                if (isInserted) {
                    Toast.makeText(QuestListActivity.this, "Data inserted", Toast.LENGTH_LONG).show();

                    //Updating listView
                    boolean isUpdate = myDb.updateData(idEditText.getText().toString(),
                            questNameEditText.getText().toString(),
                            DEADLINE_DEFAULT,
                            TYPE_DEFAULT);

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
            }
        });

        // Initializing the deleteQuestBtn and its listener
        deleteQuestBtn = (Button) findViewById(R.id.delete_button);
        deleteQuestBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(idEditText.getText().toString());

                if (deletedRows > 0) {
                    Toast.makeText(QuestListActivity.this, deletedRows + " rows deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QuestListActivity.this, "No data deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showMessage(String title, String message) {
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
                Snackbar.make(getCurrentFocus(), "You selected about", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            case MENU_ITEM_LOGOUT:
                Snackbar.make(getCurrentFocus(), "You selected Logout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            case R.id.action_shopping_cart:
                Snackbar.make(getCurrentFocus(), "You selected Shopping cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
