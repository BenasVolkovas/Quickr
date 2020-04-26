package com.example.quickr;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class TasksDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
