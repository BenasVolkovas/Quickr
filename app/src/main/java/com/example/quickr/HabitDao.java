package com.example.quickr;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Multiple sqlite queries for database
@Dao
public interface HabitDao {
    @Query("INSERT INTO habits (content, streak, checked) VALUES ('New habit', 0, 0)")
    void create();

    @Query("SELECT * FROM habits")
    List<Habit> getAll();

    @Query("UPDATE habits SET content = :content WHERE id = :id")
    void saveText(String content, int id);

    @Query("UPDATE habits SET streak = streak+1 WHERE id = :id")
    void updateStreak(int id);

    @Query("UPDATE habits SET streak = streak-1 WHERE id = :id")
    void removeStreak(int id);

    @Query("UPDATE habits SET streak = 0 WHERE id = :id")
    void deleteStreak(int id);

    @Query("SELECT streak FROM habits WHERE id = :id")
    int getStreak(int id);

    @Query("UPDATE habits SET checked = 1 WHERE id = :id")
    void isChecked(int id);

    @Query("UPDATE habits SET checked = 0 WHERE id = :id")
    void notChecked(int id);

    @Query("SELECT checked FROM habits WHERE id = :id")
    int getCheckInfo(int id);

    @Query("SELECT updateTime FROM habits WHERE id = :id")
    String getLastupdate(int id);

    @Query("UPDATE habits SET updateTime = :date WHERE id = :id")
    void updateDate(String date, int id);

    @Query("DELETE FROM habits WHERE id = :id")
    void delete(int id);
}