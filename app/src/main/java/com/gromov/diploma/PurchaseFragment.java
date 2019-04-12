package com.gromov.diploma;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import androidx.room.Room;

import static android.app.Activity.RESULT_OK;

public class PurchaseFragment extends Fragment {

    public static final int FILE_PICKER_REQUEST_CODE = 1;
    public static final int PERMISSIONS_REQUEST_CODE = 0;

    com.github.clans.fab.FloatingActionButton load_file_btn, load_manually_btn;
    TextView text;


    private PurchaseDao purchaseDao;
    private DatabasePurchase db;


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




        text = view.findViewById(R.id.id_purchase_text);

        load_file_btn = view.findViewById(R.id.load_file_check);
        load_file_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionsAndOpenFilePicker();
            }
        });

        createDb();

        load_manually_btn = view.findViewById(R.id.load_manually_check);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

            if (path != null) {
                Log.d("Path: ", path);

                String fileText = String.valueOf(Utils.readText(path));

                Gson gson = new Gson();
                Purchase purchase = gson.fromJson(fileText, Purchase.class);

                purchaseDao.insertAll(purchase);
                Purchase byName = purchaseDao.findByCost("42400.0");
                text.setText(byName.showPurchase());
            }
        }
    }

    public void createDb(){
        Context context = getContext();
        db = DatabasePurchase.getInstanse(context);
        purchaseDao = db.purchaseDao();
    }

    public void closeDb() throws IOException {
        db.close();
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
