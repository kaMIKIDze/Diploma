package com.gromov.diploma.view.products;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.gromov.diploma.R;
import com.gromov.diploma.data.async.AddPurchaseAsyncTask;
import com.gromov.diploma.data.async.GetCategoryAsyncTask;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;
import com.gromov.diploma.data.database.entities.Product;
import com.gromov.diploma.data.database.entities.Purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CreatePurchaseActivity extends AppCompatActivity {

    private String[] categoriesNames;
    private TextInputEditText placeName;
    private List<Category> categories;
    private List<Product> products;
    private DatabasePurchase databasePurchase;
    private AppCompatSpinner spinnerCategory;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;
    private FloatingActionButton addProduct;
    private Purchase purchase;
    private AppCompatTextView purchaseTotalSum;
    private double totalSum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        createDb();
        toolbar = findViewById(R.id.create_purchase_toolbar);
        setSupportActionBar(toolbar);
        products = new ArrayList<>();

        placeName = findViewById(R.id.place_name);

        purchaseTotalSum = findViewById(R.id.purchase_total_sum);

        spinnerCategory = findViewById(R.id.spinnerCategory);

        try {
            categories = new GetCategoryAsyncTask(databasePurchase).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        categoriesNames = GetStringArray(categories);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setSelection(1);


        recyclerView = findViewById(R.id.recycler_view_purchase);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CreatePurchaseAdapter(products);
        recyclerView.setAdapter(mAdapter);

        addProduct = findViewById(R.id.create_purchase_fab);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreatePurchaseActivity.this, CreateProductActivity.class);
                i.putExtra("category", String.valueOf(spinnerCategory.getSelectedItem()));
                i.putExtra("id_category", String.valueOf(spinnerCategory.getSelectedItemId()));
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ProductInfo productInfo = data.getParcelableExtra("product");
        Product product = new Product(productInfo);
        products.add(product);
        for (int i = 0; i < products.size(); i++) {
            totalSum += products.get(i).getSum();
        }
        purchaseTotalSum.setText(String.valueOf(totalSum / 100.0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_purchase_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_purchase:
                purchase = new Purchase();
                if (String.valueOf(placeName.getText()).isEmpty())
                    Toast.makeText(CreatePurchaseActivity.this, "you must enter a value to converted",
                            Toast.LENGTH_SHORT).show();
                else
                    purchase.setRetailPlaceAddress(String.valueOf(placeName.getText()));
                purchase.setItems(products);
                purchase.setEcashTotalSum((float) totalSum);
                new AddPurchaseAsyncTask(databasePurchase, purchase).execute();
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public static String[] GetStringArray(List<Category> arr) {

        String str[] = new String[arr.size()];

        for (int j = 0; j < arr.size(); j++) {
            str[j] = arr.get(j).toString();
        }

        return str;
    }


    public void createDb() {
        databasePurchase = DatabasePurchase.getInstanse(this);
    }
}
