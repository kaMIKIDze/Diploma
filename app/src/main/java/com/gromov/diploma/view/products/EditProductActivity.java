package com.gromov.diploma.view.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.gromov.diploma.R;

public class EditProductActivity extends ProductActivity {

    private ProductInfo productInfoParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_product);
        productInfoParent = getIntent().getParcelableExtra("product");

        super.onCreate(savedInstanceState);
    }


    public void setWiews() {
        super.setWiews();

        productName.setText(productInfoParent.getName());

        category.setSelection(productInfoParent.getCategoryId() - 1);
        countProduct.setText(String.valueOf(productInfoParent.getCount()));
        costProduct.setText(String.valueOf(productInfoParent.getCost() / 100.0));
        countingSum();
    }


    public void countingSum() {
        double count = Double.parseDouble(String.valueOf(countProduct.getText()));
        double cost = (Double.parseDouble(String.valueOf(costProduct.getText())));
        String str = String.format(getString(R.string.with_sum_product), count * cost);
        totalSum.setText(str);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_product:
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

                super.onOptionsItemSelected(item);

                Intent intent = new Intent();
                intent.putExtra("product", productInfo);
                intent.putExtra("product_parent", productInfoParent);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
