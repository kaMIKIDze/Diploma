package com.gromov.diploma;

import android.os.AsyncTask;

public class AgentAsyncTask extends AsyncTask {

    private PurchaseDao purchaseDao;
    private Purchase purchase;
    private ProductDao productDao;

    public AgentAsyncTask(DatabasePurchase databasePurchase, Purchase purchase) {
        this.purchase = purchase;
        this.purchaseDao = databasePurchase.purchaseDao();
        this.productDao = databasePurchase.productDao();
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        long ownerId = purchaseDao.insertAll(purchase);
        purchase.setOwnerIdItems(ownerId);
        productDao.insertAll(purchase.getItems());

        return null;
    }


}
