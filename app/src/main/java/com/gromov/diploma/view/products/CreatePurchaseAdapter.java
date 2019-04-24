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

public class CreatePurchaseAdapter extends RecyclerView.Adapter<CreatePurchaseAdapter.MyViewHolder> {

    private List<Product> products;

    public CreatePurchaseAdapter(List<Product> products) {
        this.products = products;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView category;
        private TextView totalSum;
        private TextView sumAndQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_product);
            category = itemView.findViewById(R.id.category_product);
            totalSum = itemView.findViewById(R.id.total_sum);
            sumAndQuantity = itemView.findViewById(R.id.sum_and_quantity);
        }
    }

    @NonNull
    @Override
    public CreatePurchaseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_create_purchase, parent, false);

        return new CreatePurchaseAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int pos) {

        Product product = products.get(pos);
        myViewHolder.name.setText(product.getName());
        String getSum = String.format("%.1f р.", product.getSum() / 100.0);
        myViewHolder.totalSum.setText(getSum);
        myViewHolder.category.setText(product.getCategory().getName());
        String sumAndQuan = String.format("%.1f x %.1f", ((product.getSum() / product.getQuantity()) / 100.0), product.getQuantity());
        myViewHolder.sumAndQuantity.setText(sumAndQuan);
    }


    @Override
    public int getItemCount() {

            return products.size();
    }


}