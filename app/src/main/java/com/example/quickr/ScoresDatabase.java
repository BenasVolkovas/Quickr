package com.example.quickr;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Score.class}, version = 1)
public abstract class ScoresDatabase extends RoomDatabase {
    public abstract ScoreDao scoreDao();
}