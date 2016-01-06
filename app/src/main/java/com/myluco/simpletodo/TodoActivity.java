package com.myluco.simpletodo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;

    //ArrayList<String> items;
    ArrayList<TodoItem> items;
    //ArrayAdapter<String> itemsAdapter;
    TodoItemsAdapter itemsAdapter;
    ListView lvItems;
    EditText etNewItem;
    int lastPosition;
    long lastId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        //readItems();
        items = new ArrayList<TodoItem>();
        List<TodoItem> queryItems = readItemsFromDB();
        //itemsAdapter = new ArrayAdapter<String>(this,
        //        android.R.layout.simple_list_item_1, items);
        itemsAdapter = new TodoItemsAdapter(this,items);
        itemsAdapter.addAll(queryItems);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();

    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        //Log.v("long clicked", "pos" + " " + position);
                        TodoItem item = items.get(position);
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        item.delete();
                        //writeItems();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Log.v("short clicked", "pos" + " " + position);
                        launchEditItem(items.get(position).description);
                        lastPosition = position;
                    }
                }
        );
    }

    public void launchEditItem(String itemText) {
        Intent i = new Intent(this,EditItemActivity.class);
        i.putExtra("itemText", itemText);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            String newText = data.getExtras().getString("itemNewText");

            TodoItem item = items.get(lastPosition);
            if (!item.description.equals(newText)) {
                Log.v("onActivityResult-will update",newText);
                item.description = newText;
                items.remove(lastPosition);
                items.add(lastPosition, item);
                itemsAdapter.notifyDataSetChanged();
                item.save();
            }
            //writeItems();
        }
    }
    private List<TodoItem> readItemsFromDB() {

        List<TodoItem> queryResults = TodoItem.getAll();
        if (queryResults.size() > 0) {
            TodoItem lastItem = queryResults.get(queryResults.size()-1);
            lastId = lastItem.remoteId;
        } else {
            lastId = 0;
        }
        return queryResults;

    }
//    private void readItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            items = new ArrayList<String>(FileUtils.readLines(todoFile));
//        } catch (IOException e) {
//            items = new ArrayList<String>();
//        }
//    }
//
//    private void writeItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            FileUtils.writeLines(todoFile, items);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private long getId() {
        lastId++;
        return lastId;
    }
    public void onAddItem(View view) {
        String itemText = etNewItem.getText().toString();
        if (!itemText.trim().isEmpty()) {
            TodoItem item = new TodoItem(getId(),itemText);
            itemsAdapter.add(item);
            item.save();
            //writeItems();
        }
        etNewItem.setText("");
    }
}