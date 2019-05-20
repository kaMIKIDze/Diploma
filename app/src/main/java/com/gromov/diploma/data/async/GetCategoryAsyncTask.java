package com.gromov.diploma.data.async;

import android.os.AsyncTask;

import com.gromov.diploma.data.database.daos.CategoryDao;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;
import java.util.List;

public class GetCategoryAsyncTask extends AsyncTask<Void, Void, List<Category>> {

    private CategoryDao categoryDao;

    public GetCategoryAsyncTask(DatabasePurchase databasePurchase){
        this.categoryDao = databasePurchase.categoryDao();
    }

    @Override
    protected List<Category> doInBackground(Void... voids) {
        return categoryDao.getAllCategory();
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        super.onPostExecute(categories);
    }
}
