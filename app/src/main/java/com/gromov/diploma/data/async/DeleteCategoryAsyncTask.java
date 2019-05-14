package com.gromov.diploma.data.async;

import android.os.AsyncTask;

import com.gromov.diploma.data.database.daos.CategoryDao;
import com.gromov.diploma.data.database.daos.ProductDao;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;

public class DeleteCategoryAsyncTask extends AsyncTask {


    private CategoryDao categoryDao;
    private ProductDao productDao;
    private Category category;

    public DeleteCategoryAsyncTask(Category category, DatabasePurchase databasePurchase) {
        this.category = category;
        this.categoryDao = databasePurchase.categoryDao();
        this.productDao = databasePurchase.productDao();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        productDao.updateCategory(category.getId());
        categoryDao.delete(category);
        return null;
    }
}
