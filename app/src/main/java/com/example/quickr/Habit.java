package com.example.quickr;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Single Habit has id and content
// Later it also will have streak
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
}