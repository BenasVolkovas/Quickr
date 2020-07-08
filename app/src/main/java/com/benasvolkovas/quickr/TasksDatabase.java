package com.benasvolkovas.quickr;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// TasksDatabase will have methods/queries created in TaskDao
@Database(entities = {Task.class}, version = 1)
public abstract class TasksDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}