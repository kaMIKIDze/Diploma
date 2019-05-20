package com.gromov.diploma.view.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.gromov.diploma.R;

public class EditProductActivity extends ProductActivity {
    private static final String PRODUCT = "product";
    private static final String TOOLBAR_TITLE = "Изменение продукта";
    private ProductInfo productInfoParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        productInfoParent = getIntent().getParcelableExtra(PRODUCT);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(TOOLBAR_TITLE);
    }


    protected void setViews() {
        super.setViews();

        productName.setText(productInfoParent.getName().trim());
        category.setSelection(getIndex(productInfoParent.getCategoryName()));
        countProduct.setText(String.valueOf(productInfoParent.getCount()));
        costProduct.setText(String.valueOf(productInfoParent.getCost() / 100.0));
        countingSum();
    }

    private int getIndex(String myString) {
        for (int i = 0; i < category.getCount(); i++) {
            if (category.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_product:
                if (!isValid()) return false;

                super.onOptionsItemSelected(item);

                Intent intent = new Intent();
                intent.putExtra(PRODUCT, productInfo);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
        return false;
    }

    private boolean isValid() {
        if (String.valueOf(productName.getText()).isEmpty()) {
            Toast.makeText(EditProductActivity.this, getString(R.string.error_product_name),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (String.valueOf(costProduct.getText()).isEmpty()) {
            Toast.makeText(EditProductActivity.this, getString(R.string.error_product_cost),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (String.valueOf(countProduct.getText()).isEmpty()) {
            Toast.makeText(EditProductActivity.this, getString(R.string.error_product_count),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
