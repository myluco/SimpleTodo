package com.myluco.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by lcc on 1/6/16.
 */
@Table(name = "TodoItems")
public class TodoItem extends Model{
    @Column(name = "remoteId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;
    // This is a regular field
    @Column(name = "description")
    public String description;

    @Column(name = "date")
    public long date =0;

    public String dateString;
    public TodoItem() {
        super();
    }

    public TodoItem(long remoteId, String description) {
        super();
        this.remoteId = remoteId;
        this.description = description;
    }
    public static List<TodoItem> getAll() {
        // This is how you execute a query
        return new Select()
          .from(TodoItem.class)
          .orderBy("remoteId ASC")
          .limit(100)
          .execute();
    }


}
