package com.gromov.diploma.view.products;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.gromov.diploma.R;
import com.gromov.diploma.data.async.GetCategoryAsyncTask;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;
import com.gromov.diploma.data.database.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CreatePurchaseActivity extends AppCompatActivity {

    private String[] categoriesNames;
    private EditText place_name;
    private List<Category> categories;
    private List<Product> products;
    private DatabasePurchase databasePurchase;
    private AppCompatSpinner spinnerCategory;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);


        createDb();
        toolbar = findViewById(R.id.create_purchase_toolbar);
        setSupportActionBar(toolbar);


        spinnerCategory = (AppCompatSpinner) findViewById(R.id.spinnerCategory);

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


        products = test();
        recyclerView = findViewById(R.id.recycler_view_purchase);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CreatePurchaseAdapter(products);
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_purchase_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public static String[] GetStringArray(List<Category> arr) {

        String str[] = new String[arr.size()];

        for (int j = 0; j < arr.size(); j++) {
            str[j] = arr.get(j).toString();
        }

        return str;
    }

    public List<Product> test() {
        List<Product> list = new ArrayList<>();
        Product product1 = new Product("Хлеб", 2530, 2.0);
        Product product2 = new Product("Мясо", 34000, 0.45);
        list.add(product1);
        list.add(product2);
        return list;
    }


    public void createDb() {
        databasePurchase = DatabasePurchase.getInstanse(this);
    }
}
