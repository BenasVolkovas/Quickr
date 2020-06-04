package com.example.quickr;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// HabitsDatabase will have methods/queries created in HabitDao
@Database(entities = {Habit.class}, version = 2)
public abstract class HabitsDatabase extends RoomDatabase {

    private static HabitsDatabase INSTANCE;

    public abstract HabitDao habitDao();

    public static HabitsDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    HabitsDatabase.class, "habits"
            )
            .allowMainThreadQueries()
            .addMigrations(migration1_2)
            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    static final Migration migration1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE habits ADD COLUMN streak INTEGER NOT NULL DEFAULT 0");
        }
    };
}