package com.benasvolkovas.quickr;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// HabitsDatabase will have methods/queries created in HabitDao
@Database(entities = {Habit.class}, version = 4)
public abstract class HabitsDatabase extends RoomDatabase {

    private static HabitsDatabase INSTANCE;

    public abstract HabitDao habitDao();

    // Builds DB with migrations
    public static HabitsDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), HabitsDatabase.class, "habits")
                    .allowMainThreadQueries()
                    .addMigrations(migration1_2, migration2_3, migration3_4)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    // Migrations
    static final Migration migration1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE habits ADD COLUMN streak INTEGER NOT NULL DEFAULT 0");
        }
    };

    static final Migration migration2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE habits ADD COLUMN checked INTEGER NOT NULL DEFAULT 0");
        }
    };

    static final Migration migration3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE habits ADD COLUMN updateTime TEXT");
        }
    };
}