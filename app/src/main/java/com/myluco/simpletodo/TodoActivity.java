package com.myluco.simpletodo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

public class TodoActivity extends AppCompatActivity implements EditItemFragment.EditItemDialogListener {


    private static Context mContext;
    //ArrayList<String> items;
    private ArrayList<TodoItem> items;

    //ArrayAdapter<String> itemsAdapter;
    private TodoItemsAdapter itemsAdapter;
    private ListView lvItems;
    private EditText etNewItem;
    private int lastPosition;
    private long lastId;
    //Need to hold the fragment to be able to call its save method.
    private EditItemFragment editItemDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        mContext = this;
        findViewsById();

        items = new ArrayList<TodoItem>();

        List<TodoItem> queryItems = readItemsFromDB();

        itemsAdapter = new TodoItemsAdapter(this, items);
        itemsAdapter.addAll(queryItems);

        lvItems.setAdapter(itemsAdapter);
        itemsAdapter.sort(itemsAdapter);

        setupListViewListener();

    }

    public static Context getAppContext() {
        return mContext;
    }

    private void findViewsById() {
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        //Log.v("long clicked", "pos" + " " + position);
                        parent.getChildAt(position).setBackgroundColor(Color.BLUE);
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
                        launchEditItem(items.get(position));
                        lastPosition = position;
                    }
                }
        );
    }

    public void launchEditItem(TodoItem item) {

        FragmentManager fm = getSupportFragmentManager();
        editItemDialog = EditItemFragment.newInstance(item);
        editItemDialog.show(fm, "fragment_edit_item");

    }

    private List<TodoItem> readItemsFromDB() {

        List<TodoItem> queryResults = TodoItem.getAll();
        if (queryResults.size() > 0) {
            TodoItem lastItem = queryResults.get(queryResults.size() - 1);
            lastId = lastItem.remoteId;
        } else {
            lastId = 0;
        }
        return queryResults;

    }


    private long getId() {
        lastId++;
        return lastId;
    }

    public void onAddItem(View view) {
        String itemText = etNewItem.getText().toString();
        if (!itemText.trim().isEmpty()) {
            TodoItem item = new TodoItem(getId(), itemText);
//            Log.v("TodoActivity-onAddItem-Created id ", String.valueOf(item.remoteId));
            item.dueDateString = Utilities.NotFinished();
            item.setPriority(TodoItem.Priority.MEDIUM);
            item.setStatus(TodoItem.Status.TODO);
            itemsAdapter.add(item);
            itemsAdapter.sort(itemsAdapter);
            item.save();
            //writeItems();
        }
        etNewItem.setText("");
    }

    /**
     * This is the method that will be called when the EditItemFragment done
     *
     * @param newItemText
     * @param newItemDate
     */
    @SuppressLint("LongLogTag")
    @Override
    public void onFinishEditItemDialog(TodoItem itemFixed) {

        String newDate = itemFixed.dueDateString;
        long longDate = Utilities.longFromDateString(newDate);
        if (longDate == 0) {
            newDate = Utilities.NotFinished();
        }
        TodoItem item = items.get(lastPosition);

        //Log.v("onActivityResult", newItemText);
        item.description = itemFixed.description;
        item.dueDateString = newDate;

        item.date = longDate;
        item.setPriority(itemFixed.priority);
//        Log.v("onFinishEditItemDialog", String.valueOf(item.priorityDB));
        item.setStatus(itemFixed.status);
//        Log.v("onFinishEditItemDialog", String.valueOf(item.statusDB));
        item.notes = itemFixed.notes;
//        Log.v("onFinishEditItemDialog", item.notes);
        items.remove(lastPosition);
        if (!item.shouldBeRemoved()) {
            items.add(lastPosition, item);
            itemsAdapter.sort(itemsAdapter);
            itemsAdapter.notifyDataSetChanged();
        }

        item.save();
    }



}