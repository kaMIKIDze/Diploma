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

        super.onCreate(savedInstanceState);
        int idSpinner = Integer.parseInt(Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).getString("id_selected_item_spinner")));
        category.setSelection(idSpinner);//
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
