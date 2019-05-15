package com.gromov.diploma.data.database.database;

import android.app.Activity;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.gromov.diploma.R;
import com.gromov.diploma.data.database.daos.CategoryDao;
import com.gromov.diploma.data.database.daos.ProductDao;
import com.gromov.diploma.data.database.daos.PurchaseDao;
import com.gromov.diploma.data.database.entities.Category;
import com.gromov.diploma.data.database.entities.Product;
import com.gromov.diploma.data.database.entities.Purchase;


@Database(entities = {Purchase.class, Product.class, Category.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class DatabasePurchase extends RoomDatabase {
    public abstract PurchaseDao purchaseDao();

    public abstract ProductDao productDao();

    public abstract CategoryDao categoryDao();

    private static DatabasePurchase bd;
    private static final int ID_DEFAULT_QUERY = -1;


    public static DatabasePurchase getInstanse(final Context context) {
        if (bd == null) {
            return Room.databaseBuilder(context,
                    DatabasePurchase.class, "database-name").addCallback(new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    String query = "Insert into category values (" + ID_DEFAULT_QUERY + ",'" + context.getString(R.string.unknown) + "', 0)";
                    db.execSQL(query);
                }
            }).build();
        } else return bd;
    }
}