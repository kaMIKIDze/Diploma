package com.gromov.diploma;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PurchaseDao {
    @Query("SELECT * FROM purchase")
    List<Purchase> getAll();

    @Query("SELECT * FROM purchase WHERE id IN (:userIds)")
    List<Purchase> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM purchase  WHERE totalSum LIKE :sum LIMIT 1")
    Purchase findByCost(String sum);

    @Insert
    void insertAll(Purchase... purchase);

    @Delete
    void delete(Purchase purchase);
}