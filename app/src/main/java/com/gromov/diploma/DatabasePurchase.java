package com.gromov.diploma;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {Purchase.class,Product.class}, version = 1)
public abstract class DatabasePurchase extends RoomDatabase {
    public abstract PurchaseDao purchaseDao();
    public abstract ProductDao productDao();

    private static DatabasePurchase bd;

    public static DatabasePurchase getInstanse(Context context) {
        if (bd == null) {
            return Room.databaseBuilder(context,
                    DatabasePurchase.class, "database-name").fallbackToDestructiveMigration().build();
        } else return bd;
    }
}