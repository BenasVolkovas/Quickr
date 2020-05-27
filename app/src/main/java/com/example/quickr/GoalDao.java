package com.example.quickr;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Multiple sqlite queries for database
@Dao
public interface GoalDao {
    @Query("INSERT INTO goals (content, time) VALUES ('New goal', '999')")
    void create();

    @Query("SELECT * FROM goals")
    List<Goal> getAll();
}