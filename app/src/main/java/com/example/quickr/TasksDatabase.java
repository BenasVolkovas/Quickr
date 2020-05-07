package com.example.quickr;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Creates database with Task Dao
@Database(entities = {Task.class}, version = 1)
public abstract class TasksDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
