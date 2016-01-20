package io.gois.bestbuycatalog.view;

import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import io.gois.bestbuycatalog.R;
import io.gois.bestbuycatalog.model.Product;
import io.gois.bestbuycatalog.task.TaskCallback;
import io.gois.bestbuycatalog.view.adapter.ProductItemAdapter;

import static io.gois.bestbuycatalog.controller.AppController.*;

public class MainActivity extends AppCompatActivity implements TaskCallback {

    private ProductItemAdapter adapter;

    private SwipeRefreshLayout swipeContainer;

    public MainActivity() {
        setVV();
        log("BEGIN MainActivity");
        getInstance().loadProducts(this);
        log("END MainActivity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log("BEGIN onCreate");

        // default actionss
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting the activity toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                getInstance().loadProducts(MainActivity.this);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        RecyclerView itemsView = (RecyclerView) findViewById(R.id.home_items);
        itemsView.setHasFixedSize(true);

        final GridLayoutManager layoutManager;
        int screenLayout = getResources().getConfiguration().screenLayout;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL)
                layoutManager = new GridLayoutManager(this, 1);
            else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
                layoutManager = new GridLayoutManager(this, 3);
            else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
                layoutManager = new GridLayoutManager(this, 4);
            else
                layoutManager = new GridLayoutManager(this, 2);
        else
            if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL)
                layoutManager = new GridLayoutManager(this, 2);
            else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
                layoutManager = new GridLayoutManager(this, 4);
            else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
                layoutManager = new GridLayoutManager(this, 5);
            else
                layoutManager = new GridLayoutManager(this, 3);
        itemsView.setLayoutManager(layoutManager);

        adapter = new ProductItemAdapter(getApplicationContext(), itemsView);
        adapter.setOnLoadMoreListener(new ProductItemAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!swipeContainer.isRefreshing())
                    getInstance().loadProducts(MainActivity.this);
            }
        });
        itemsView.setAdapter(adapter);


        log("END onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        log("BEGIN onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.home_menu, menu);
/*
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // configure the search info and add any event listeners...
*/

        boolean ret = super.onCreateOptionsMenu(menu);

        log("END onCreateOptionsMenu");

        return ret;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void doSomething(Object o) {
        log("BEGIN doSomething");

        adapter.addAll((ArrayList<Product>) o);
        adapter.notifyDataSetChanged();

        if (swipeContainer.isRefreshing())
            swipeContainer.setRefreshing(false);

        log("END doSomething");

        adapter.setLoaded();
    }

    private static final String CATEG = "MainActivity";
    private static int v = 0;
    private int vv;
    private void setVV() {
        vv = v++;
    }
    private void log(String message) {
        Log.v(CATEG + vv, message);
    }
}
