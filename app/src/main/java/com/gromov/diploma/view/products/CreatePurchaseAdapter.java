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
    private PurchaseAdapterClickListener listener;

    CreatePurchaseAdapter(List<Product> products, PurchaseAdapterClickListener listener) {
        this.products = products;
        this.listener = listener;
    }



    @NonNull
    @Override
    public CreatePurchaseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_create_purchase, parent, false);


        return new CreatePurchaseAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int pos) {
        myViewHolder.bind(pos);
    }


    @Override
    public int getItemCount() {

        return products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView category;
        private TextView totalSum;
        private TextView sumAndQuantity;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_product);
            category = itemView.findViewById(R.id.category_product);
            totalSum = itemView.findViewById(R.id.total_sum);
            sumAndQuantity = itemView.findViewById(R.id.sum_and_quantity);
        }

        void bind(final int pos) {
            final Product product = products.get(pos);

            name.setText(product.getName());
            totalSum.setText(String.format(totalSum.getContext().getString(R.string.with_sum_product), product.getSum() / 100.0));
            category.setText(product.getCategory().getName());
            sumAndQuantity.setText(String.format(totalSum.getContext().getString(R.string.with_sum_and_quan), ((product.getSum() / product.getQuantity()) / 100.0), product.getQuantity()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(product, pos);
                }
            });
        }
    }

}
