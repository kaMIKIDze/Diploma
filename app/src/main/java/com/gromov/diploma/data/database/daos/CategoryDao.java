package com.gromov.diploma.data.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gromov.diploma.data.database.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Category> getAllCategory();

    @Query("SELECT * FROM category  WHERE name LIKE :name LIMIT 1")
    Category findByCost(String name);

    @Query("SELECT * FROM category WHERE id IN (:userId)")
    Category findById(int userId);

    @Insert
    void insertAll(Category category);

    @Delete
    void delete(Category category);
}
