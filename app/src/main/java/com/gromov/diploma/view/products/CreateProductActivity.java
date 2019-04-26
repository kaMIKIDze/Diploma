package com.gromov.diploma.view.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.gromov.diploma.R;

import java.util.Objects;

public class CreateProductActivity extends ProductActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_product);
        int idParentCategory = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("id_category")));

        super.onCreate(savedInstanceState);

        category.setSelection(idParentCategory);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_product:
                if (String.valueOf(productName.getText()).isEmpty()) {
                    Toast.makeText(CreateProductActivity.this, getString(R.string.error_product_name),
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (String.valueOf(costProduct.getText()).isEmpty()) {
                    Toast.makeText(CreateProductActivity.this, getString(R.string.error_product_cost),
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (String.valueOf(countProduct.getText()).isEmpty()) {
                    Toast.makeText(CreateProductActivity.this, getString(R.string.error_product_count),
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

                super.onOptionsItemSelected(item);
                Intent intent = new Intent();
                intent.putExtra("product", productInfo);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
