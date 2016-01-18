package gois.io.bestbuycatalog.view.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import gois.io.bestbuycatalog.model.Product;
import gois.io.bestbuycatalog.view.ProductItemView;

import static android.graphics.Color.WHITE;

public class ProductItemAdapter extends BaseAdapter {

    private Context context;
    private final Product[] products;
    private final ViewGroup.LayoutParams params;

    public ProductItemAdapter(Context context, Product[] products, ViewGroup.LayoutParams params) {
        this.context = context;
        this.products = products;
        this.params = params;
    }

    @Override
    public int getCount() {
        return products.length;
    }

    @Override
    public Object getItem(int position) {
        return products[position];
    }

    @Override
    public long getItemId(int position) {
        return products[position].getId();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductItemView view = new ProductItemView(context);

        view.setLayoutParams(params);
        view.setProduct(products[position]);

        return view.build(convertView, parent);
    }
}
