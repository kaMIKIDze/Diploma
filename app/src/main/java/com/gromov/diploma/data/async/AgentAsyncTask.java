package com.gromov.diploma.data.async;

import android.os.AsyncTask;

import com.gromov.diploma.data.database.daos.ProductDao;
import com.gromov.diploma.data.database.daos.PurchaseDao;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Purchase;

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
