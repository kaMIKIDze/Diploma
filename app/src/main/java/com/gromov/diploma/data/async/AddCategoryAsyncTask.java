package com.gromov.diploma.data.async;

import android.os.AsyncTask;

import com.gromov.diploma.data.database.daos.CategoryDao;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;

public class AddCategoryAsyncTask extends AsyncTask {

    private CategoryDao categoryDao;
    private Category category;

    public AddCategoryAsyncTask(Category category, DatabasePurchase databasePurchase) {
        this.category = category;
        this.categoryDao = databasePurchase.categoryDao();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        categoryDao.insertAll(category);
        return null;
    }
}
