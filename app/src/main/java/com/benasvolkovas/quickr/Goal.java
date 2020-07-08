package com.benasvolkovas.quickr;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Single Goal has id, content, time
@Entity(tableName = "goals")
public class Goal {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "time")
    public String time;
}