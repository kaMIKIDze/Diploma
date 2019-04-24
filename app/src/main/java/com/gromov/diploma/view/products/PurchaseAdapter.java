package com.gromov.diploma.view.products;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gromov.diploma.R;
import com.gromov.diploma.data.database.entities.Purchase;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.MyViewHolder> {

    private List<Purchase> purchases;

    public PurchaseAdapter(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView textViewName;
        private TextView textViewSum;
        private TextView textViewNameItems;
        private TextView textViewQuantityItems;
        private TextView textViewPriceItems;

        public MyViewHolder(View v) {
            super(v);
            textViewName = v.findViewById(R.id.purchase_name_text);
            textViewSum = v.findViewById(R.id.purchase_sum_text);
            textViewNameItems = v.findViewById(R.id.purchase_items_name_text);
            textViewQuantityItems = v.findViewById(R.id.purchase_items_quantity_text);
            textViewPriceItems = v.findViewById(R.id.purchase_items_price_text);
        }
    }


    @Override
    public PurchaseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_purchase, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int pos) {
        String itemsName = new String("");
        String itemsQuantity = new String("");
        String itemsPrice = new String("");
        Purchase purchase = purchases.get(pos);
        myViewHolder.textViewName.setText(purchase.getRetailPlaceAddress());
        myViewHolder.textViewSum.setText("Итого: " + String.valueOf(purchase.getEcashTotalSum() / 100));
        for (int i = 0; i < purchase.getItems().size(); i++) {
            itemsName += purchase.getItems().get(i).getName() + "\n";
            itemsQuantity += String.valueOf(purchase.getItems().get(i).getQuantity() + "\n");
            itemsPrice += String.valueOf(purchase.getItems().get(i).getSum() / 100.0) + " р." + "\n";
        }
        myViewHolder.textViewNameItems.setText(itemsName);
        myViewHolder.textViewQuantityItems.setText(String.valueOf(itemsQuantity));
        myViewHolder.textViewPriceItems.setText(String.valueOf(itemsPrice));
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }
}
