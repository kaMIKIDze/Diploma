package com.gromov.diploma.view.category;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.gromov.diploma.R;
import com.gromov.diploma.data.async.AddCategoryAsyncTask;
import com.gromov.diploma.data.database.database.DatabasePurchase;
import com.gromov.diploma.data.database.entities.Category;

public class CreateCategoryActivity extends Activity {

    private Button saveBtn;
    private EditText nameCategory;
    private RadioButton required;
    private DatabasePurchase databasePurchase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        saveBtn = findViewById(R.id.save_category_button);
        nameCategory = findViewById(R.id.edit_name_category);
        required = findViewById(R.id.quantity_radio);
        required.setChecked(true);
        createDb();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q;
                String name = String.valueOf(nameCategory.getText());
                if (required.isChecked()) q = 1;
                else q = 0;
                Category category = new Category(name, q);
                new AddCategoryAsyncTask(category, databasePurchase).execute();
                finish();
            }
        });
    }


    public void createDb() {
        databasePurchase = DatabasePurchase.getInstanse(this);
    }
}
