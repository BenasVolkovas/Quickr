package com.example.quickr;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Multiple sqlite queries for database
@Dao
public interface TaskDao {
    @Query("INSERT INTO tasks (content) VALUES ('New task')")
    void create();

    @Query("SELECT * FROM tasks")
    List<Task> getAll();

    @Query("UPDATE tasks SET content = :content WHERE id = :id")
    void save(String content, int id);

    @Query("DELETE FROM tasks WHERE id = :id")
    void delete(int id);
}