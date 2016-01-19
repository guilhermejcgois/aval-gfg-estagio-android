package gois.io.bestbuycatalog.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gois.io.bestbuycatalog.R;
import gois.io.bestbuycatalog.model.Product;
import gois.io.bestbuycatalog.task.DownloadImageTask;

import static android.graphics.Color.WHITE;

/**
 * TODO: document your custom view class.
 */
public class ProductItemView extends GridView {
    private final String CATEG = "ProductItemView";

    private Product product;

    private View view;

    public ProductItemView(Context context) {
        super(context);
        init(null, 0);
    }

    public ProductItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ProductItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        updateView();
    }

    private void init(AttributeSet attrs, int defStyle) {
        Log.i(CATEG, "Initializing...");

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);

        Log.i(CATEG, "Initialized...");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public View build(View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.product_item_view, parent, false);
        }

        convertView.setBackgroundColor(WHITE);
        convertView.setElevation(2.0f);
        this.view = convertView;

        if (product == null)
            return  this.view;

        return updateView();
    }

    public View updateView() {
        Log.v(CATEG, "Updating view...");
        ImageView image = (ImageView) view.findViewById(R.id.productImageView);
        //image.setMinimumHeight(parent.getHeight() / 4);
        new DownloadImageTask(image).execute(product.getUrlItemImage());

        TextView name = (TextView) view.findViewById(R.id.nameTextView);
        String pName = product.getName();
        pName = pName.substring(pName.indexOf('-') + 2);
        name.setText(product.getName());

        TextView brand = (TextView) view.findViewById(R.id.brandTextView);
        brand.setText(product.getBrand());

        TextView price = (TextView) view.findViewById(R.id.priceTextView);
        price.setText("$ " + product.getPrice());

        return view;
    }
}
