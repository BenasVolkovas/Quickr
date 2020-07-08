package com.benasvolkovas.quickr;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Multiple sqlite queries for database
@Dao
public interface GoalDao {
    @Query("INSERT INTO goals (content, time) VALUES ('New goal', '0 / 0 / 0')")
    void create();

    @Query("SELECT * FROM goals")
    List<Goal> getAll();

    @Query("UPDATE goals SET content = :content WHERE id = :id")
    void saveText(String content, int id);

    @Query("UPDATE goals SET time = :time WHERE id = :id")
    void saveDate(String time, int id);

    @Query("DELETE FROM goals WHERE id = :id")
    void delete(int id);
}