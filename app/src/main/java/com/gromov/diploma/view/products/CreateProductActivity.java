package com.gromov.diploma.view.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.gromov.diploma.R;

import java.util.Objects;

public class CreateProductActivity extends ProductActivity {

    private static final String PRODUCT = "product";
    private static final String ID_SELECTED_ITEM_SPINNER = "id_selected_item_spinner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        int idSpinner = Integer.parseInt(Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).getString(ID_SELECTED_ITEM_SPINNER)));
        category.setSelection(idSpinner);//
        getSupportActionBar().setTitle(getString(R.string.add_product));
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
                intent.putExtra(PRODUCT, productInfo);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
