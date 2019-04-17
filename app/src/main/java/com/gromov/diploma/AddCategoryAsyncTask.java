package com.gromov.diploma;

import android.os.AsyncTask;

public class AddCategoryAsyncTask extends AsyncTask {

    private CategoryDao categoryDao;
    private Category category;

    AddCategoryAsyncTask(Category category, CategoryDao categoryDao) {
        this.category = category;
        this.categoryDao = categoryDao;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        categoryDao.insertAll(category);
        return null;
    }
}
