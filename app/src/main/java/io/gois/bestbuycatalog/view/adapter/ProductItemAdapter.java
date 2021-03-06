package io.gois.bestbuycatalog.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.gois.bestbuycatalog.R;
import io.gois.bestbuycatalog.model.Product;
import io.gois.bestbuycatalog.task.DownloadImageTask;
import io.gois.bestbuycatalog.view.ProductDetailActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.graphics.Color.WHITE;

/**
 * <code>Adapter</code> for <code>RecyclerView</code> in <code>MainActivity</code>.
 */
public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ViewHolder> {

    private List<Product> dataset;

    /**
     * The context of the <code>RecyclerView</code> owner activity.
     */
    private Context context;

    /**
     * The owner of this adapter.
     */
    private RecyclerView recyclerView;

    /**
     * Indicates that exists items being loaded.
     */
    private boolean loading = false;

    /**
     * Indicates the number of items loaded.
     */
    private int totalItemCount;

    /**
     * Indicate the last visible item.
     */
    private int lastVisibleItem;

    /**
     * How much items can be more displayed.
     */
    private int visibleThreshold = 2;

    /**
     * Listener to when more items needs to be loaded.
     */
    private OnLoadMoreListener onLoadMoreListener;

    /**
     * Class constructos.
     *
     * @param context the <code>context</code> of the <code>Activity</code> that owns the
     *                <code>recycleView</code>.
     * @param recyclerView the <code>RecyclerView</code> that owns this adapter.
     */
    public ProductItemAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;

        this.dataset = new ArrayList<>();

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {

            final GridLayoutManager linearLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
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

    /**
     * Sets the flag meaning that the new items are loaded.
     */
    public void setLoaded() {
        loading = false;
    }

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
        String pName = holder.product.getName().substring(holder.product.getName().indexOf('-') + 2);
        name.setText(pName);

        TextView brand = (TextView) view.findViewById(R.id.brandTextView);
        brand.setText(holder.product.getBrand());

        TextView price = (TextView) view.findViewById(R.id.priceTextView);
        String sprice = "$ " + holder.product.getPrice();
        price.setText(sprice);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    /**
     * Sets the <code>onLoadMoreListener</code>.
     *
     * @param onLoadMoreListener the <code>OnLoadMoreListener</code> to listen when new items are
     *                           loaded.
     */
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

                    intent.putExtra("product", product);
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

                    ProductItemAdapter.this.context.startActivity(intent);
                }
            });
        }
    }
}
