package com.gromov.diploma.view.products;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.gromov.diploma.R;
import com.gromov.diploma.data.storage.FileSystem;
import com.gromov.diploma.data.async.AddCategoryAsyncTask;
import com.gromov.diploma.data.async.AgentAsyncTask;
import com.gromov.diploma.data.async.GetPurchaseAsyncTask;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;
import com.gromov.diploma.data.database.entities.Purchase;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;

public class PurchaseFragment extends Fragment {

    private static final int FILE_PICKER_REQUEST_CODE = 1;
    private static final int PERMISSIONS_REQUEST_CODE = 0;

    private FloatingActionButton loadFileBtn;
    private DatabasePurchase databasePurchase;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Purchase> purchases;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void openFilePicker() {
        new MaterialFilePicker()
                .withSupportFragment(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .withTitle("Sample title")
                .start();
    }

    private void showError() {
        Log.d("Error: ", "Allow external storage reading");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.purchase_fragment, container, false);


        loadFileBtn = view.findViewById(R.id.fab);
        loadFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionsAndOpenFilePicker();
            }
        });

        createDb();
        GetPurchaseAsyncTask getPurchaseAsyncTask = new GetPurchaseAsyncTask(databasePurchase);
        getPurchaseAsyncTask.execute();
        try {
            purchases = getPurchaseAsyncTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ProductsAdapter(purchases);
        recyclerView.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

            if (path != null) {
                String fileText = String.valueOf(FileSystem.readText(path));

                Gson gson = new Gson();
                Purchase purchase = gson.fromJson(fileText, Purchase.class);

                Category category = new Category();

                new AddCategoryAsyncTask(category, databasePurchase).execute();
                new AgentAsyncTask(databasePurchase, purchase).execute();
            }
        }
    }

    public void createDb() {
        Context context = getContext();
        databasePurchase = DatabasePurchase.getInstanse(context);
    }

    public void closeDb() throws IOException {
        databasePurchase.close();
    }


    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            openFilePicker();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFilePicker();
                } else {
                    showError();
                }
            }
        }
    }


}
