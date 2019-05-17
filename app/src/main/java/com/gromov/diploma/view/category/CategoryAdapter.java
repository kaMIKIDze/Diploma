package com.gromov.diploma.view.category;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gromov.diploma.R;
import com.gromov.diploma.data.database.entities.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categories;

    CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryText;

        CategoryViewHolder(View view) {
            super(view);
            categoryText = view.findViewById(R.id.text_card_category);

        }

    }


    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_category, parent, false);

        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder myViewHolder, int i) {

        String nameCategory;
        Category category = categories.get(i);
        nameCategory = category.getName();
        myViewHolder.categoryText.setText(nameCategory);


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    void removeItem(int position) {
        categories.remove(position);
        notifyItemRemoved(position);
    }

    public List<Category> getData() {
        return categories;
    }

    void restoreItem(Category item, int position) {
        categories.add(position, item);
        notifyItemInserted(position);
    }

}

