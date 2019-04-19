package com.gromov.diploma.view.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gromov.diploma.R;
import com.gromov.diploma.data.async.GetCategoryAsyncTask;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class CategoryFragment extends Fragment {

    private FloatingActionButton addCategoryBtn;
    private List<Category> categories;
    private RecyclerView recyclerView;
    private DatabasePurchase databasePurchase;
    private CategoryAdapter categoryAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        createDb();
        GetCategoryAsyncTask getCategoryAsyncTask = new GetCategoryAsyncTask(databasePurchase);
        getCategoryAsyncTask.execute();
        try {
            categories = getCategoryAsyncTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addCategoryBtn = view.findViewById(R.id.fab_category);
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CreateCategory.class));

            }
        });

        recyclerView = view.findViewById(R.id.category_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(categories);
        recyclerView.setAdapter(categoryAdapter);

        return view;
    }


    public void createDb() {
        Context context = getContext();
        databasePurchase = DatabasePurchase.getInstanse(context);
    }

}
