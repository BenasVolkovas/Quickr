package com.example.quickr;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {
    @Query("INSERT INTO scores (points) VALUES (0)")
    void create();

    @Query("UPDATE scores SET points = points + :amount")
    void updatePoints(int amount);

    @Query("UPDATE scores SET points = points - :amount")
    void removePoints(int amount);

    @Query("SELECT * FROM scores")
    Score getAll();

    @Query("DELETE FROM scores")
    void delete();
}
