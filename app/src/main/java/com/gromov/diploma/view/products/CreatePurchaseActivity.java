package com.gromov.diploma.view.products;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gromov.diploma.R;
import com.gromov.diploma.data.async.AddPurchaseAsyncTask;
import com.gromov.diploma.data.async.GetCategoryAsyncTask;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;
import com.gromov.diploma.data.database.entities.Product;
import com.gromov.diploma.data.database.entities.Purchase;
import com.gromov.diploma.data.storage.FileSystem;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class CreatePurchaseActivity extends AppCompatActivity {
    final int REQUEST_CODE_ADD = 1;
    final int REQUEST_CODE_EDIT = 2;
    private static final int FILE_PICKER_REQUEST_CODE = 3;
    private static final int PERMISSIONS_REQUEST_CODE = 0;


    private TextInputEditText placeName;
    private List<Category> categories;
    private List<Product> products;
    private DatabasePurchase databasePurchase;
    private AppCompatSpinner spinnerCategory;
    private CreatePurchaseAdapter mAdapter;
    private AppCompatTextView purchaseTotalSum;
    private Calendar calendar = Calendar.getInstance();
    private double totalSum;
    private int position;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton addProduct;
    private FloatingActionButton addCheck;
    private Button dateButton;
    private Date date = calendar.getTime();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        createDb();
        createViews();

        setSupportActionBar(toolbar);
        products = new ArrayList<>();
        getCategories();
        dateButton.setText(DateUtils.formatDateTime(this,
                date.getTime(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

        embodySpinner();
        embodyRecyclerView();
        setClickListener();
        getSupportActionBar().setTitle("Создание покупки");
    }

    private void createViews() {
        placeName = findViewById(R.id.place_name);
        purchaseTotalSum = findViewById(R.id.purchase_total_sum);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        toolbar = findViewById(R.id.create_purchase_toolbar);
        recyclerView = findViewById(R.id.recycler_view_purchase);
        addProduct = findViewById(R.id.create_purchase_fab);
        addCheck = findViewById(R.id.create_purchase_fab_check);
        dateButton = findViewById(R.id.date_button);
    }

    private Category getCategoryByName(String categoryName) {
        for (Category category : categories) {
            if (category.getName() == categoryName) return category;
        }
        return null;
    }

    private void getCategories() {

        try {
            categories = new GetCategoryAsyncTask(databasePurchase).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void setClickListener() {
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(CreatePurchaseActivity.this, CreateProductActivity.class);
                    i.putExtra("category", String.valueOf(spinnerCategory.getSelectedItem()));
                    i.putExtra("id_category", String.valueOf(getCategoryByName((String) spinnerCategory.getSelectedItem()).getId()));//индексы листа начинаются от 0, а в БД от 1
                    i.putExtra("id_selected_item_spinner", String.valueOf(spinnerCategory.getSelectedItemId()));
                    startActivityForResult(i, REQUEST_CODE_ADD);
                } catch (Exception e){
                    Toast.makeText(CreatePurchaseActivity.this, "Нет категории, требуется создать новую категорию",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        addCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionsAndOpenFilePicker();
            }
        });
    }

    private void setDate(View v) {
        new DatePickerDialog(v.getContext(), begin,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener begin = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateButton.setText(DateUtils.formatDateTime(CreatePurchaseActivity.this,
                    calendar.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
            date = calendar.getTime();
        }
    };

    private void embodySpinner() {
        String[] categoriesNames = GetStringArray(categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setSelection(0);
    }

    private void embodyRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CreatePurchaseAdapter(products, new PurchaseAdapterClickListener() {
            @Override
            public void onItemClick(Product item, int pos) {
                position = pos;
                Intent i = new Intent(CreatePurchaseActivity.this, EditProductActivity.class);
                ProductInfo productInfo = new ProductInfo(item);
                i.putExtra("product", productInfo);
                i.putExtra("id_category", String.valueOf(getCategoryByName((String) spinnerCategory.getSelectedItem()).getId()));
                startActivityForResult(i, REQUEST_CODE_EDIT);
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_CANCELED) super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            ActivityResultAdd(data);
        } else if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            ActivityResultFilePicker(data);
        } else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            ActivityResultEdit(data);
        }
    }

    private void ActivityResultAdd(Intent data) {
        ProductInfo productInfo = data.getParcelableExtra("product");
        Product product = new Product(productInfo);
        products.add(product);
        totalSum = 0;
        for (int i = 0; i < products.size(); i++) {
            totalSum += products.get(i).getSum();
        }
        purchaseTotalSum.setText(String.valueOf(totalSum / 100.0));
        mAdapter.notifyDataSetChanged();
    }

    private void ActivityResultFilePicker(Intent data) {
        String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

        if (path != null) {
            String fileText = String.valueOf(FileSystem.readText(path));
            Gson gson = new Gson();
            Purchase purchase = gson.fromJson(fileText, Purchase.class);
            List<Product> productsFromCheck = purchase.getItems();
            totalSum = 0;
            for (int i = 0; i < productsFromCheck.size(); i++) {
                productsFromCheck.get(i).setCategory(new Category((int) categories.get((int) (spinnerCategory.getSelectedItemId())).getId(), String.valueOf(spinnerCategory.getSelectedItem())));
                productsFromCheck.get(i).setCategoryId(categories.get((int) (spinnerCategory.getSelectedItemId())).getId());
            }
            products.addAll(purchase.getItems());
            for (int i = 0; i < products.size(); i++) {
                totalSum += products.get(i).getSum();
            }
            purchaseTotalSum.setText(String.valueOf(totalSum / 100.0));
            mAdapter.notifyDataSetChanged();
        }
    }

    private void ActivityResultEdit(Intent data) {
        ProductInfo productInfo = data.getParcelableExtra("product");
        Product product = new Product(productInfo);
        products.set(position, product);
        totalSum = 0;
        for (int i = 0; i < products.size(); i++) {
            totalSum += products.get(i).getSum();
        }
        purchaseTotalSum.setText(String.valueOf(totalSum / 100.0));
        mAdapter.notifyItemChanged(position);
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
                Purchase purchase = new Purchase();
                if (String.valueOf(placeName.getText()).isEmpty()) {
                    Toast.makeText(CreatePurchaseActivity.this, getString(R.string.error_place_name),
                            Toast.LENGTH_SHORT).show();
                    return false;
                } else
                    purchase.setRetailPlaceAddress(String.valueOf(placeName.getText()));
                purchase.setItems(products);
                purchase.setEcashTotalSum((float) totalSum);
                purchase.setCurrentTime(date);
                new AddPurchaseAsyncTask(databasePurchase, purchase).execute();
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    private void openFilePicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withFilter(Pattern.compile(".*\\.json$"))
                .withHiddenFiles(true)
                .withTitle("Sample title")
                .start();
    }

    private void showError() {
        Toast.makeText(CreatePurchaseActivity.this, getString(R.string.error_reading_storage), Toast.LENGTH_SHORT).show();
    }


    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            openFilePicker();
        }
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
