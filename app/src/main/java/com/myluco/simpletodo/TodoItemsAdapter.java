package com.myluco.simpletodo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.SortedSet;

/**
 * Created by lcc on 1/6/16.
 */
public class TodoItemsAdapter extends ArrayAdapter<TodoItem> implements Comparator<TodoItem>{



    public TodoItemsAdapter(Context context, ArrayList<TodoItem> items) {
        super(context, 0, items);


    }

    private int increaseSaturationValue(int c) {
        float[] hsv = new float[3];
        Color.colorToHSV(c,hsv);
        hsv[1] = 100;
        hsv[2]= 100;
        return Color.HSVToColor(hsv);
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
        TextView tvPriority = (TextView)convertView.findViewById(R.id.tvPriority);

        // Populate the data into the template view using the data object
        tvDescription.setText(item.description);
        Date date;
        if (item.date != 0) {

            item.dueDateString = Utilities.dateStringFromLong(item.date);
        } else {
            item.dueDateString = Utilities.NotFinished();
        }
//        Log.v("TodoItemAdapter-Item", String.valueOf(item.getId()));
        tvDate.setText(item.dueDateString);
//        Log.v("TodoItemAdapter-PriorityDB", String.valueOf(item.priorityDB));
        int color=0;
        switch (item.priorityDB) {
            case 0:
                tvPriority.setText(getContext().getString(R.string.high));
                item.priority = TodoItem.Priority.HIGH;
//                convertView.setBackgroundColor(Color.MAGENTA);
                color = convertView.getResources().getColor(R.color.Tomato);
                convertView.setBackgroundColor(color);
//                convertView.setBackgroundColor(0xFFFF4500);
                break;
            case 1:
                tvPriority.setText(getContext().getString(R.string.medium));
                item.priority = TodoItem.Priority.MEDIUM;
//                convertView.setBackgroundColor(0xFFFFE4C4);
                color = convertView.getResources().getColor(R.color.DarkSeaGreen);
                convertView.setBackgroundColor(color);
                break;
            case 2:
                tvPriority.setText(getContext().getString(R.string.low));
                item.priority = TodoItem.Priority.LOW;
                color = convertView.getResources().getColor(R.color.LightSteelBlue);
                convertView.setBackgroundColor(color);
                break;

        }
        //increase saturation of color if task is late
        if (item.isLate()) {
           int anotherColor = increaseSaturationValue(color);
            convertView.setBackgroundColor(anotherColor);
        }


//         Log.v("TodoItemAdapter-StatusDB", String.valueOf(item.statusDB));
        switch (item.statusDB) {
            case 0:
                item.status = TodoItem.Status.TODO;
                break;
            case 1:
                item.status = TodoItem.Status.DONE;
                break;


        }
        // Return the completed view to render on screen
        return convertView;

    }




    @Override
    public int compare(TodoItem lhs, TodoItem rhs) {
        return lhs.compareTo(rhs);
    }
}

