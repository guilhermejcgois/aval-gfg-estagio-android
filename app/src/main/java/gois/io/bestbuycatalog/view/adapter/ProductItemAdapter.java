package gois.io.bestbuycatalog.view.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import gois.io.bestbuycatalog.model.Product;
import gois.io.bestbuycatalog.view.ProductItemView;

import static android.graphics.Color.WHITE;

public class ProductItemAdapter extends ArrayAdapter<Product> {

    public ProductItemAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductItemView view = new ProductItemView(getContext());

        view.setProduct(getItem(position));

        return view.build(convertView, parent);
    }
}
