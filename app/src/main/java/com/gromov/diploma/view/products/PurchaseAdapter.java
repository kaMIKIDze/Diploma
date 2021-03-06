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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {

    private List<Purchase> purchases;
    View v;


    PurchaseAdapter(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewSum;
        private TextView textViewDate;
        private RecyclerView recyclerViewProduct;
        private RecyclerView.Adapter mAdapter;


        private RecyclerView.LayoutManager layoutManager;

        public ViewHolder(View v) {
            super(v);
            textViewName = v.findViewById(R.id.purchase_name_text);
            textViewSum = v.findViewById(R.id.purchase_sum_text);
            textViewDate = v.findViewById(R.id.date_text);
            recyclerViewProduct = v.findViewById(R.id.products_recycler_view);
            layoutManager = new LinearLayoutManager(v.getContext());
            recyclerViewProduct.setLayoutManager(layoutManager);

        }
    }


    @Override
    public PurchaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_purchase, parent, false);

        return new ViewHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder myViewHolder, int pos) {

        Purchase purchase = purchases.get(pos);
        myViewHolder.textViewName.setText(purchase.getRetailPlaceAddress());
        myViewHolder.textViewSum.setText(v.getContext().getString(R.string.total) + " "
                + String.valueOf(purchase.getEcashTotalSum() / 100) + " "
                + v.getContext().getString(R.string.currency_unit_rus));
        DateFormat df = new SimpleDateFormat(v.getContext().getString(R.string.date_format));
        String reportDate = df.format(purchase.getCurrentTime());

        myViewHolder.textViewDate.setText(reportDate);
        List<Product> products = purchase.getItems();
        myViewHolder.mAdapter = new ProductDisplayAdapter(products);
        myViewHolder.recyclerViewProduct.setAdapter(myViewHolder.mAdapter);

    }

    @Override
    public int getItemCount() {
        if (purchases == null) return 0;
        else
            return purchases.size();
    }
}
