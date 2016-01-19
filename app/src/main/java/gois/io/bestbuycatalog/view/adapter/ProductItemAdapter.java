package gois.io.bestbuycatalog.view.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gois.io.bestbuycatalog.R;
import gois.io.bestbuycatalog.model.Product;
import gois.io.bestbuycatalog.task.DownloadImageTask;
import gois.io.bestbuycatalog.view.MainActivity;
import gois.io.bestbuycatalog.view.ProductDetailActivity;
import gois.io.bestbuycatalog.view.ProductItemView;

import static android.graphics.Color.WHITE;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ViewHolder> {

    private List<Product> dataset;

    private Context context;

    private RecyclerView recyclerView;

    private boolean loading = false;
    private int totalItemCount;
    private int lastVisibleItem;
    private int visibleThreshold = 2;
    private OnLoadMoreListener onLoadMoreListener;

    public ProductItemAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;

        this.dataset = new ArrayList<>();

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoaded() {
        loading = false;
    }

    private final String CATEG = "ProductItemAdapter";
    private static int v = 0;
    private int vv;
    private void setVV() {
        vv = v++;
    }
    private void log(String message) {
        Log.v(CATEG + vv, message);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ProductItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_view, parent, false);

        view.setBackgroundColor(WHITE);
        ((GridLayout) view).setColumnCount(3);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        View view = holder.view;
        holder.product = dataset.get(position);

        ImageView image = (ImageView) view.findViewById(R.id.productImageView);
        //image.setMinimumHeight(parent.getHeight() / 4);
        new DownloadImageTask(image).execute(holder.product.getUrlItemImage());

        TextView name = (TextView) view.findViewById(R.id.nameTextView);
        String pName = holder.product.getName();
        pName = pName.substring(pName.indexOf('-') + 2);
        name.setText(holder.product.getName());

        TextView brand = (TextView) view.findViewById(R.id.brandTextView);
        brand.setText(holder.product.getBrand());

        TextView price = (TextView) view.findViewById(R.id.priceTextView);
        price.setText("$ " + holder.product.getPrice());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void clear() {
        dataset.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Product> moreProducts) {
        dataset.addAll(moreProducts);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected View view;
        protected Product product;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProductItemAdapter.this.context, ProductDetailActivity.class);

                    intent.putExtra("product", (Serializable) product);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    ProductItemAdapter.this.context.startActivity(intent);
                }
            });
        }
    }
}
