package gois.io.bestbuycatalog.view;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import gois.io.bestbuycatalog.R;
import gois.io.bestbuycatalog.model.Product;
import gois.io.bestbuycatalog.task.DownloadImageTask;

public class ProductDetailActivity extends AppCompatActivity {

    public ProductDetailActivity() {
        log("ProductDetailActivity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        log("BEGIN onCreate");

        setContentView(R.layout.activity_product_detail);

        // setting the activity toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        // enabling up button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            Product product = (Product) intent.getSerializableExtra("product");
            new DownloadImageTask((ImageView) findViewById(R.id.productImageView)).execute(product.getUrlDetailImage());
            ((TextView) findViewById(R.id.nameTextView)).setText(product.getName());
            ((TextView) findViewById(R.id.brandTextView)).setText(product.getBrand());
            String pPrice = "$ " + product.getPrice();
            ((TextView) findViewById(R.id.priceTextView)).setText(pPrice);
            ((TextView) findViewById(R.id.descriptionTextView)).setText(product.getDescription());
        }

        log("END onCreate");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        log("onOptionsItemSelected");

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static final String CATEG = "ProductDetailActivity";
    private void log(String message) {
        Log.v(CATEG, message);
    }
}
