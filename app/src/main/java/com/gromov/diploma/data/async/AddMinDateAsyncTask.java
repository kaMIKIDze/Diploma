package com.gromov.diploma.data.async;

import android.os.AsyncTask;

import com.gromov.diploma.data.database.daos.PurchaseDao;
import com.gromov.diploma.data.database.database.DatabasePurchase;

import java.util.Date;

public class AddMinDateAsyncTask extends AsyncTask<Void,Void, Date> {

    private PurchaseDao purchaseDao;

    public AddMinDateAsyncTask (DatabasePurchase databasePurchase){
        purchaseDao = databasePurchase.purchaseDao();
    }

    @Override
    protected Date doInBackground(Void... voids) {

        return purchaseDao.findMinDate();
    }

    @Override
    protected void onPostExecute(Date date) {
        super.onPostExecute(date);
    }
}
