package com.gromov.diploma.view.category;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gromov.diploma.R;
import com.gromov.diploma.data.async.DeleteCategoryAsyncTask;
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
    private View view;
    private CoordinatorLayout coordinatorLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        getActivity().setTitle(getString(R.string.category));


        initDatabase();
        createViews();
        getCategories();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        getCategories();
    }


    private void enableSwipeToDeleteAndUndo() {
        CategorySwipeController swipeToDeleteCallback = new CategorySwipeController(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final Category item = categoryAdapter.getData().get(position);

                categoryAdapter.removeItem(position);

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, getString(R.string.cancel_deletion), Snackbar.LENGTH_LONG);
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                            new DeleteCategoryAsyncTask(item, databasePurchase).execute();
                        }
                    }
                });
                snackbar.setAction(getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        categoryAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });


                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    public void createViews() {

        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        recyclerView = view.findViewById(R.id.category_recycler_view);
        addCategoryBtn = view.findViewById(R.id.fab_category);

        enableSwipeToDeleteAndUndo();

        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateCategoryActivity.class));

            }
        });

    }


    public void getCategories() {
        GetCategoryAsyncTask getCategoryAsyncTask = new GetCategoryAsyncTask(databasePurchase);
        getCategoryAsyncTask.execute();
        try {
            categories = getCategoryAsyncTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        layoutManager = new LinearLayoutManager(getContext());
        categoryAdapter = new CategoryAdapter(categories);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoryAdapter);

    }

    public void initDatabase() {
        databasePurchase = DatabasePurchase.getInstanse(getContext());
    }

}
