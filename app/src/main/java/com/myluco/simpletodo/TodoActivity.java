package com.myluco.simpletodo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                        launchEditItem(items.get(position).description, items.get(position).dateString);
                        lastPosition = position;
                    }
                }
        );
    }

    public void launchEditItem(String itemText, String itemDate) {

        FragmentManager fm = getSupportFragmentManager();
        editItemDialog = EditItemFragment.newInstance(itemText, itemDate);
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
            item.dateString = Utilities.NotFinished();
            itemsAdapter.add(item);
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
    public void onFinishEditItemDialog(String newItemText, String newItemDate) {

        String newDate = newItemDate;
        long longDate = Utilities.longFromDateString(newDate);
        if (longDate == 0) {
            newDate = Utilities.NotFinished();
        }
        TodoItem item = items.get(lastPosition);

        Log.v("onActivityResult", newItemText);
        item.description = newItemText;
        item.dateString = newDate;

        item.date = longDate;
        items.remove(lastPosition);
        items.add(lastPosition, item);
        itemsAdapter.notifyDataSetChanged();
        item.save();
    }


    /**
     * The click method for the button save in the EditItemFragment needs to be defined in an
     * activity. So I chose the activity that calls it, and immediatelly calls the method that is
     * being defined in the EditItemFragment.
     *
     * @param view
     */
    public void onSaveEditItemDialog(View view) {
        editItemDialog.onSave(view);
    }
}