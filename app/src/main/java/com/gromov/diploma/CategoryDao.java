package com.gromov.diploma;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Category> getAllProduct();

    @Query("SELECT * FROM category WHERE id IN (:userIds)")
    List<Category> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM category WHERE id IN (:Ids)")
    List<Category> loadAllByOwnerIds(int[] Ids);

    @Query("SELECT * FROM category  WHERE name LIKE :name LIMIT 1")
    Category findByCost(String name);

    @Insert
    void insertAll(Category category);

    @Delete
    void delete(Category category);
}
