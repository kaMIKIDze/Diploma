package com.gromov.diploma;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class AgentAsyncTask extends AsyncTask {

    PurchaseDao purchaseDao;
    Purchase purchase;
    ProductDao productDao;
    TextView textView;
    Purchase byName = null;
    List<Product> products;
    String text;

    @Override
    protected Object doInBackground(Object[] objects) {
        long ownerId = purchaseDao.insertAll(purchase);

        byName = purchaseDao.findByCost("42400.0");

        Log.i("Item: ",byName.showPurchase());

        purchase.setOwnerIdItems(ownerId);
        productDao.insertAll(purchase.getItems());

        products = productDao.getAllProduct();

        for (int i=0;i<products.size();i++)
            text +=products.get(i).showProduct()+" "+products.get(i).ownerId;

        //text=byName.showPurchase()+" "+byName.id;
        return byName;
    }

    public AgentAsyncTask(ProductDao productDao, PurchaseDao purchaseDao, Purchase purchase, TextView textView){
        this.purchase = purchase;
        this.purchaseDao = purchaseDao;
        this.textView = textView;
        this.productDao = productDao;
    }

    @Override
    protected void onPostExecute(Object o) {
        textView.setText(text);
    }
}
