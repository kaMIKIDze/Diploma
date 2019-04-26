package com.gromov.diploma.view.products;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.gromov.diploma.R;
import com.gromov.diploma.data.async.GetCategoryAsyncTask;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;

import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class ProductActivity extends AppCompatActivity {
    protected TextInputEditText productName;
    protected AppCompatSpinner category;
    protected TextInputEditText countProduct;
    protected TextInputEditText costProduct;
    protected AppCompatTextView totalSum;
    protected List<Category> categories;
    protected DatabasePurchase databasePurchase;
    protected String[] categoriesNames;
    protected Toolbar productToolbar;
    protected ProductInfo productInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_product);
        super.onCreate(savedInstanceState);

        createDb();
        createViews();
        setWiews();
    }

    public void createViews() {
        productToolbar = findViewById(R.id.create_product_toolbar);
        productName = findViewById(R.id.product_name);
        category = findViewById(R.id.product_category_spinner);
        countProduct = findViewById(R.id.count_product);
        costProduct = findViewById(R.id.cost_product);
        totalSum = findViewById(R.id.product_total_sum);
    }


    public void setWiews() {

        setSupportActionBar(productToolbar);

        setCategories();
        createAdapter();

        countProduct.addTextChangedListener(textWatcher());
        costProduct.addTextChangedListener(textWatcher());

    }

    public void setCategories() {
        try {
            categories = new GetCategoryAsyncTask(databasePurchase).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createAdapter() {
        categoriesNames = GetStringArray(categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
    }

    public void countingSum() {
        double count = Double.parseDouble(String.valueOf(countProduct.getText()));
        double cost = (Double.parseDouble(String.valueOf(costProduct.getText())));
        String str = String.format(getString(R.string.with_sum_product), count * cost);
        totalSum.setText(str);
    }


    public TextWatcher textWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double count;
                double cost;

                if (!String.valueOf(countProduct.getText()).isEmpty())
                    count = Double.parseDouble(String.valueOf(countProduct.getText()));
                else count = 1;
                if (!String.valueOf(costProduct.getText()).isEmpty())
                    cost = Double.parseDouble(String.valueOf(costProduct.getText()));
                else cost = 1;

                String str = String.format(getString(R.string.with_sum_product), count * cost);
                totalSum.setText(str);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_product_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_product:
                productInfo = new ProductInfo(String.valueOf(productName.getText()),
                        (int) category.getSelectedItemId(),
                        categories.get((int) category.getSelectedItemId()).getName(),
                        Double.parseDouble(String.valueOf(countProduct.getText())),
                        Double.parseDouble(String.valueOf(costProduct.getText())));

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
