package com.example.quickr;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Multiple sqlite queries for database
@Dao
public interface HabitDao {
    @Query("INSERT INTO habits (content) VALUES ('New habit')")
    void create();

    @Query("SELECT * FROM habits")
    List<Habit> getAll();

    @Query("UPDATE habits SET content = :content WHERE id = :id")
    void save(String content, int id);

    @Query("DELETE FROM habits WHERE id = :id")
    void delete(int id);
}