package com.benasvolkovas.quickr;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Single Habit
@Entity(tableName = "habits")
public class Habit {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "streak")
    public int streak;

    @ColumnInfo(name = "checked")
    public int checked;

    @ColumnInfo(name = "updateTime")
    public String updateTime;
}