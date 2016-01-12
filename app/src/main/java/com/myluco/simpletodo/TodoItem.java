package com.myluco.simpletodo;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Comparator;
import java.util.List;

/**
 * Created by lcc on 1/6/16.
 */
@Table(name = "TodoItems")
public class TodoItem extends Model implements Comparable<TodoItem> {



    public static enum Status {TODO, DONE};
    public static enum Priority {HIGH,MEDIUM,LOW};



    @Column(name = "remoteId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;
    // This is a regular field
    @Column(name = "description", length=50)
    public String description;

    @Column(name = "date")
    public long date =0;

    //priority 0=low; 1= medium 2 = high
    @Column(name="priorityDB")
    public int priorityDB;
    public String priorityString="";
    public Priority priority;

    @Column(name="notes")
    public String notes="";

    //status = 0 = to-do status = 1 => done
    @Column(name="statusDB")
    public int statusDB;
    public Status status;

    public String dueDateString;

    public TodoItem() {
        super();
    }

    public TodoItem(long remoteId, String description) {
        super();
        this.remoteId = remoteId;
        this.description = description;
    }

    @Override
    public int compareTo(TodoItem another) {
        if (priorityDB > another.priorityDB  ) {
            return 1;
        }
        else if (priorityDB < another.priorityDB) {
            return -1;
        }
        return 0;
    }



    public static List<TodoItem> getAll() {
        // This is how you execute a query
        List<TodoItem> items = new Select()
          .from(TodoItem.class)
          .orderBy("priorityDB,date DESC")
          .where("statusDB = 0")
          .limit(100)
          .execute();
//        for (TodoItem item: items) {
//            Log.v("TodoItem-GetAll-Id/Priority",String.valueOf(item.getId())+"/"+String.valueOf(item.priorityDB));
//
//        }
        return items;
    }
    public void setPriority(Priority p) {
        priorityDB =  p.ordinal();
        priority = p;
//        Log.v("TodoItem-Priority = ", String.valueOf(priorityDB));

    }
    public void setStatus(Status s) {
        status = s;
       statusDB = s.ordinal();
//        Log.v("TodoItem-Status = ", String.valueOf(statusDB));
    }
    public boolean shouldBeRemoved() {
        if (status == Status.DONE) {
            return true;
        }
        return false;
    }

}
