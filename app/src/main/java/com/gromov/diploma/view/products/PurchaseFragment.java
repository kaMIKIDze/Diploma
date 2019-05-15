package com.gromov.diploma.view.products;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gromov.diploma.R;
import com.gromov.diploma.data.async.GetPurchaseAsyncTask;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Purchase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PurchaseFragment extends Fragment {


    private DatabasePurchase databasePurchase;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Purchase> purchases;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.purchase_fragment, container, false);

        getActivity().setTitle(getString(R.string.purchase));
        FloatingActionButton loadFileBtn = view.findViewById(R.id.fab);
        loadFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreatePurchaseActivity.class));
            }
        });

        initDatabase();
        recyclerView = view.findViewById(R.id.recycler_view);
        getPurchase();


        return view;
    }

    public void getPurchase() {
        GetPurchaseAsyncTask getPurchaseAsyncTask = new GetPurchaseAsyncTask(databasePurchase);
        getPurchaseAsyncTask.execute();
        try {
            purchases = getPurchaseAsyncTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PurchaseAdapter(purchases);
        recyclerView.setAdapter(mAdapter);
    }

    public void initDatabase() {
        databasePurchase = DatabasePurchase.getInstanse(getContext());

    }

    @Override
    public void onStart() {
        getPurchase();
        super.onStart();
    }

}
