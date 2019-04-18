package com.gromov.diploma;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface PurchaseDao {
    @Query("SELECT * FROM purchase")
    List<Purchase> getAllPurshase();

    @Query("SELECT * FROM purchase WHERE id IN (:userIds)")
    List<Purchase> loadAllByIds(int[] userIds);

    @Insert
    long insertAll(Purchase purchase);

    @Delete
    void delete(Purchase purchase);
}