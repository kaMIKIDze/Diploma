package com.gromov.diploma;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Purchase.class}, version = 1)
public abstract class DatabasePurchase extends RoomDatabase {
    public abstract PurchaseDao purchaseDao();

    private static DatabasePurchase bd;

    public static  DatabasePurchase getInstanse(Context context) {
        if (bd == null) {
            return Room.databaseBuilder(context,
                    DatabasePurchase.class, "database-name").build();
        }else return bd;
    }
}