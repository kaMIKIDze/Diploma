package com.gromov.diploma;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAllProduct();

    @Query("SELECT * FROM product WHERE id IN (:userIds)")
    List<Product> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM product WHERE ownerId IN (:Ids)")
    List<Product> loadAllByOwnerIds(int[] Ids);

    @Query("SELECT * FROM product  WHERE sum LIKE :sum LIMIT 1")
    Product findByCost(int sum);

    @Insert
    void insertAll(List<Product> products);

    @Delete
    void delete(Product product);
}

