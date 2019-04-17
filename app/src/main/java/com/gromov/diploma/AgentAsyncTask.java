package com.gromov.diploma;

import android.os.AsyncTask;

import java.util.List;

public class AgentAsyncTask extends AsyncTask {

    private PurchaseDao purchaseDao;
    private Purchase purchase;
    private ProductDao productDao;
    private List<Product> products;


    @Override
    protected Object doInBackground(Object[] objects) {

        long ownerId = purchaseDao.insertAll(purchase);
        purchase.setOwnerIdItems(ownerId);

        productDao.insertAll(purchase.getItems());
        products = productDao.getAllProduct();

        return null;
    }

    public AgentAsyncTask(ProductDao productDao, PurchaseDao purchaseDao, Purchase purchase) {
        this.purchase = purchase;
        this.purchaseDao = purchaseDao;
        this.productDao = productDao;
    }

}
