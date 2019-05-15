package com.gromov.diploma.data.async;

import android.os.AsyncTask;

import com.gromov.diploma.data.database.daos.ProductDao;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Product;

public class DeleteProductAsyncTask extends AsyncTask {

    private Product product;
    private ProductDao productDao;

    public DeleteProductAsyncTask(Product product, DatabasePurchase databasePurchase) {
        this.product = product;
        this.productDao = databasePurchase.productDao();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        productDao.delete(product);
        return null;
    }
}
