package com.gromov.diploma.view.products;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gromov.diploma.R;
import com.gromov.diploma.data.database.entities.Product;

import java.util.List;

public class ProductDisplayAdapter extends RecyclerView.Adapter<ProductDisplayAdapter.ViewHolder> {

    private List<Product> products;
    private final static int MAX_SIZE = 5;
    View v;

    ProductDisplayAdapter(List<Product> products) {
        this.products = products;
    }


    @NonNull
    @Override
    public ProductDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder myViewHolder, int i) {
        myViewHolder.textViewNameItems.setText(products.get(i).getName());
        myViewHolder.textViewPriceItems.setText(String.valueOf(products.get(i).getSum() / 100.0) + v.getContext().getString(R.string.currency_unit_rus));
        myViewHolder.textViewQuantityItems.setText(String.valueOf(products.get(i).getQuantity()));
    }

    @Override
    public int getItemCount() {
        boolean flag = false;
        if (flag ||products.size()<MAX_SIZE) return products.size();
        else return MAX_SIZE;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNameItems;
        private TextView textViewQuantityItems;
        private TextView textViewPriceItems;

        ViewHolder(@NonNull View v) {
            super(v);
            textViewNameItems = v.findViewById(R.id.purchase_items_name_text);
            textViewQuantityItems = v.findViewById(R.id.purchase_items_quantity_text);
            textViewPriceItems = v.findViewById(R.id.purchase_items_price_text);

        }
    }
}
