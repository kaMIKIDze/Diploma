package com.gromov.diploma.data.async;

import android.os.AsyncTask;

import com.gromov.diploma.data.database.daos.CategoryDao;
import com.gromov.diploma.data.database.daos.ProductDao;
import com.gromov.diploma.data.database.daos.PurchaseDao;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;
import com.gromov.diploma.data.database.entities.Product;
import com.gromov.diploma.data.database.entities.Purchase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GetPurchaseAsyncTask extends AsyncTask<Void, Void, List<Purchase>> {
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private PurchaseDao purchaseDao;
    private List<Category> categories;
    private Date dataBegin = new Date(Long.MIN_VALUE);
    private Date dataEnd = Calendar.getInstance().getTime();
    private boolean isShowFullList;


    public GetPurchaseAsyncTask(DatabasePurchase databasePurchase) {
        productDao = databasePurchase.productDao();
        categoryDao = databasePurchase.categoryDao();
        purchaseDao = databasePurchase.purchaseDao();
        isShowFullList = true;
    }

    public GetPurchaseAsyncTask(DatabasePurchase databasePurchase, Date dataBegin, Date dateEnd) {
        productDao = databasePurchase.productDao();
        categoryDao = databasePurchase.categoryDao();
        purchaseDao = databasePurchase.purchaseDao();
        this.dataBegin = dataBegin;
        this.dataEnd = dateEnd;
        isShowFullList = false;
    }


    @Override
    protected List<Purchase> doInBackground(Void... voids) {
        List<Purchase> purchases;
        if (isShowFullList) {
            purchases = purchaseDao.getAllPurshase();
        } else purchases = purchaseDao.findBetweenDates(dataBegin, dataEnd);

        categories = categoryDao.getAllCategoryByStat();
        for (Purchase purchase : purchases) {
            List<Product> products = productDao.loadAllByOwnerId(purchase.getId());
            for (Product product : products) {
                product.setCategory(getCategoryById(product.getCategoryId()));
            }

            purchase.setItems(products);
        }
        return purchases;
    }

    private Category getCategoryById(int categoryId) {
        for (Category category : categories) {
            if (category.getId() == categoryId) return category;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Purchase> purchases) {
        super.onPostExecute(purchases);
    }
}
