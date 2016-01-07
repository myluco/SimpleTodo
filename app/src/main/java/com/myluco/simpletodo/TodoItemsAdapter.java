package com.myluco.simpletodo;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lcc on 1/6/16.
 */
public class TodoItemsAdapter extends ArrayAdapter<TodoItem> {


    public TodoItemsAdapter(Context context, ArrayList<TodoItem> users) {
        super(context, 0, users);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        // Lookup view for data population
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        // Populate the data into the template view using the data object
        tvDescription.setText(item.description);
        Date date;
        if (item.date != 0) {

            item.dateString = Utilities.dateStringFromLong(item.date);
        } else {
            item.dateString = Utilities.NotFinished();
        }
        tvDate.setText(item.dateString);
        // Return the completed view to render on screen
        return convertView;

    }
}

