package com.gromov.diploma.data.async;

import android.os.AsyncTask;

import com.gromov.diploma.data.database.daos.CategoryDao;
import com.gromov.diploma.data.database.daos.ProductDao;
import com.gromov.diploma.data.database.daos.PurchaseDao;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;
import com.gromov.diploma.data.database.entities.Product;
import com.gromov.diploma.data.database.entities.Purchase;

import java.util.List;

public class GetPurchaseAsyncTask extends AsyncTask<Void, Void, List<Purchase>> {
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private PurchaseDao purchaseDao;
    private List<Category> categories;


    public GetPurchaseAsyncTask(DatabasePurchase databasePurchase) {
        productDao = databasePurchase.productDao();
        categoryDao = databasePurchase.categoryDao();
        purchaseDao = databasePurchase.purchaseDao();
    }


    @Override
    protected List<Purchase> doInBackground(Void... voids) {
        List<Purchase> purchases = purchaseDao.getAllPurshase();
        categories = categoryDao.getAllCategory();
        for (Purchase purchase : purchases) {
            List<Product> products = productDao.loadAllByOwnerId(purchase.getId());
            for (Product product : products) {
                product.setCategory(getCategoryById(product.getCategoryId()));
            }

           purchase.setItems(products);
        }
        return purchases;
    }

    public Category getCategoryById(int categoryId){
        for(Category category : categories){
            if (category.getId() == categoryId) return  category;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Purchase> purchases) {
        super.onPostExecute(purchases);
    }
}
