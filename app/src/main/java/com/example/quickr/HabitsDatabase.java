package com.example.quickr;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// HabitsDatabase will have methods/queries created in HabitDao
@Database(entities = {Habit.class}, version = 1)
public abstract class HabitsDatabase extends RoomDatabase {

    public abstract HabitDao habitDao();

//    static final Migration migration1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE habits ADD COLUMN streak INTEGER DEFAULT '0'");
//        }
//    };
//
//    Room.databaseBuilder(getApplicationContext(), HabitsDatabase.class, "habits")
//        .addMigrations(migratio1_2).build();
}