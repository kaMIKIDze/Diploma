package com.gromov.diploma.data.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gromov.diploma.data.database.entities.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAllProduct();

    @Query("SELECT * FROM product WHERE id IN (:Id)")
    List<Product> loadAllById(int[] Id);

    @Query("SELECT * FROM product WHERE ownerId IN (:Id)")
    List<Product> loadAllByOwnerId(int Id);

    @Insert
    void insertAll(List<Product> products);

    @Delete
    void delete(Product product);
}

