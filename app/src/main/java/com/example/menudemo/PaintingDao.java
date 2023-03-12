package com.example.menudemo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PaintingDao {
    @Query("SELECT * FROM paintings")
    List<PaintingEntity> getPaintings();

    @Query("SELECT * from paintings WHERE id = :id")
    PaintingEntity getPaintingById(Long id);

    @Insert
    void insert(PaintingEntity... paintings);

    @Update
    void update(PaintingEntity... paintings);

    @Delete
    void delete(PaintingEntity painting);
}
