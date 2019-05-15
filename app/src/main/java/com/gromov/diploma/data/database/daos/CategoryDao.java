package com.gromov.diploma.data.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gromov.diploma.data.database.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category WHERE id > 0")
    List<Category> getAllCategory();

    @Query("SELECT * FROM category")
    List<Category> getAllCategoryByStat();

    @Query("SELECT * FROM category WHERE id IN (:Id)")
    Category findById(int Id);

    @Insert
    void insertAll(Category category);

    @Delete
    void delete(Category category);
}
