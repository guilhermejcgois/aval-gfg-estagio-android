package gois.io.bestbuycatalog.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gois.io.bestbuycatalog.R;
import gois.io.bestbuycatalog.controller.AppController;
import gois.io.bestbuycatalog.model.Product;
import gois.io.bestbuycatalog.task.TaskCallback;
import gois.io.bestbuycatalog.view.adapter.ProductItemAdapter;

public class MainActivity extends AppCompatActivity implements TaskCallback {

    private ProductItemAdapter adapter;

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView itemsView;

    private boolean taskIsRunning = false;

    public MainActivity() {
        setVV();
        log("BEGIN MainActivity");
        AppController.getInstance().loadProducts(this);
        taskIsRunning = true;
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
                AppController.getInstance().loadProducts(MainActivity.this);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        itemsView = (RecyclerView) findViewById(R.id.home_items);
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
                    AppController.getInstance().loadProducts(MainActivity.this);
            }
        });
        itemsView.setAdapter(adapter);


        log("END onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        log("BEGIN onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.home_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // configure the search info and add any event listeners...

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
        taskIsRunning = false;

        if (swipeContainer.isRefreshing())
            swipeContainer.setRefreshing(false);

        log("END doSomething");

        adapter.setLoaded();
    }

    private final String CATEG = "MainActivity";
    private static int v = 0;
    private int vv;
    private void setVV() {
        vv = v++;
    }
    private void log(String message) {
        Log.v(CATEG + vv, message);
    }
}
