package com.example.quickr;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// HabitsDatabase will have methods/queries created in HabitDao
@Database(entities = {Habit.class}, version = 1)
public abstract class HabitsDatabase extends RoomDatabase {
    public abstract HabitDao habitDao();
}