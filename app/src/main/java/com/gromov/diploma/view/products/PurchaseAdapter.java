package com.gromov.diploma.view.products;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gromov.diploma.R;
import com.gromov.diploma.data.database.entities.Product;
import com.gromov.diploma.data.database.entities.Purchase;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.MyViewHolder> {

    private List<Purchase> purchases;
    View v;


    public PurchaseAdapter(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewSum;
        private RecyclerView recyclerViewProduct;
        private RecyclerView.Adapter mAdapter;


        private RecyclerView.LayoutManager layoutManager;

        public MyViewHolder(View v) {
            super(v);
            textViewName = v.findViewById(R.id.purchase_name_text);
            textViewSum = v.findViewById(R.id.purchase_sum_text);
            recyclerViewProduct = v.findViewById(R.id.products_recycler_view);
            layoutManager = new LinearLayoutManager(v.getContext());
            recyclerViewProduct.setLayoutManager(layoutManager);
        }
    }


    @Override
    public PurchaseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_purchase, parent, false);

        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int pos) {
        Purchase purchase = purchases.get(pos);
        myViewHolder.textViewName.setText(purchase.getRetailPlaceAddress());
        myViewHolder.textViewSum.setText(v.getContext().getString(R.string.total) + String.valueOf(purchase.getEcashTotalSum() / 100));
        List<Product> products = purchase.getItems();
        myViewHolder.mAdapter = new ProductDisplayAdapter(products);
        myViewHolder.recyclerViewProduct.setAdapter(myViewHolder.mAdapter);

    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }
}
