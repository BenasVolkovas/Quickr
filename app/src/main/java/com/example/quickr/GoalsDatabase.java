package com.example.quickr;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// GoalsDatabase will have methods/queries created in GoalDao
@Database(entities = {Goal.class}, version = 1)
public abstract class GoalsDatabase extends RoomDatabase {
    public abstract GoalDao goalDao();
}