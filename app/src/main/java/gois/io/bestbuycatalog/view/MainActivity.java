package gois.io.bestbuycatalog.view;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private final String CATEG = "MainActivity";

    private List<Product> products;

    private ProductItemAdapter adapter;

    private GridView itemsView;

    private boolean taskIsRunning = false;

    public MainActivity() {
        AppController.getInstance().loadProducts(this);
        taskIsRunning = true;
        products = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(CATEG, "Creating...");

        // default actions
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting the activity toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        /*
        Product p = new Product();
        p.setId(0);
        p.setName("BestBuy Product");
        p.setBrand("BestBuy Brand");
        p.setPrice(99.99);
        p.setUrlItemImage("https://corporate.bestbuy.com/wp-content/themes/bbyignitepossible/library/images/best-buy-small.png");
        p.setUrlDetailImage("https://corporate.bestbuy.com/wp-content/themes/bbyignitepossible/library/images/best-buy-small.png");
        p.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed a pharetra felis, vitae consequat metus. Vivamus vulputate est quam, quis egestas risus hendrerit nec. Praesent lectus lorem, volutpat feugiat lorem vel, sollicitudin consequat est. Nullam porta dictum augue et efficitur. Sed tincidunt ex vel tellus tempus, eget semper metus blandit. Vivamus quis gravida justo. Morbi nulla quam, finibus sed nisl in, luctus venenatis arcu. Mauris venenatis mauris eget risus rutrum lobortis. Ut facilisis, nisl at fermentum aliquet, ante sem commodo dui, nec bibendum lacus quam at massa. Duis facilisis accumsan nisl. Quisque lacus erat, rutrum a quam vitae, dapibus elementum lorem.\n" +
                "\n" +
                "Donec imperdiet interdum neque a consectetur. Aliquam erat metus, ornare sed iaculis scelerisque, iaculis et justo. Nunc auctor sed mi in facilisis. Suspendisse velit massa, sollicitudin et fringilla sed, hendrerit a nisl. Curabitur posuere magna nec lacus mattis iaculis. Nunc mollis id est non rhoncus. Fusce a diam sed nunc imperdiet posuere. Donec volutpat lacinia lorem vel tempus. Suspendisse suscipit felis vel ante scelerisque, ut rhoncus sapien auctor. Phasellus sit amet porta orci. Quisque ut auctor lorem. Cras nec porta turpis, vitae bibendum massa. Duis aliquam quis nisi nec condimentum. Nam at mollis nulla. Aenean nec quam vulputate, facilisis mauris vel, tristique enim.\n" +
                "\n" +
                "Integer quis lacus et dolor malesuada laoreet. Donec odio nisi, maximus at tempor vel, fermentum at sem. Nullam vel eros nec augue iaculis varius a quis velit. In sit amet arcu rutrum, tristique enim sit amet, cursus sem. Ut eu nibh neque. Morbi rutrum ex quis dui bibendum rhoncus a molestie ipsum. Cras est tortor, auctor sit amet ligula a, consequat aliquet tortor. Etiam at quam eget odio elementum ullamcorper. Phasellus semper a elit vel blandit. Proin ornare interdum vestibulum. Aliquam ac quam placerat, ultrices magna eu, auctor risus.\n" +
                "\n" +
                "Etiam ac laoreet sapien, sodales rutrum purus. Proin eleifend egestas tempor. Proin at ex dolor. Fusce nibh eros, maximus non erat eu, rhoncus hendrerit dui. Curabitur cursus urna sit amet velit lacinia suscipit. Proin non ex consequat, vulputate erat quis, consequat metus. Nunc a nunc ut est laoreet egestas. Donec id augue vestibulum libero pretium lobortis et vitae augue. Donec a commodo leo.\n" +
                "\n" +
                "Fusce auctor, lectus a iaculis posuere, lectus lacus bibendum massa, congue facilisis diam dolor hendrerit nunc. Phasellus sollicitudin odio libero, non imperdiet odio aliquet eu. Morbi odio ipsum, luctus at auctor a, pulvinar sed ante. Duis nisi neque, eleifend a nisi ut, aliquam ullamcorper orci. Maecenas mauris leo, imperdiet nec lobortis ac, aliquam vitae elit. Fusce in justo vitae risus aliquet tincidunt. Curabitur sit amet ante sodales, iaculis turpis at, lobortis felis. Mauris sollicitudin in mi ac elementum. Donec blandit diam at tempor auctor. Vivamus porta nisi aliquam metus maximus, suscipit tincidunt lorem volutpat.");
        products[0] = p;
        p.setId(1);
        products[1] = p;
        p.setId(2);
        products[2] = p;
        p.setId(3);
        products[3] = p;
        p.setId(4);
        products[4] = p;
        */

        adapter = new ProductItemAdapter(this, R.id.home_items, products);

        itemsView = (GridView) findViewById(R.id.home_items);
        itemsView.setNumColumns(2);
        itemsView.setAdapter(adapter);
        itemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                intent.putExtra("product", (Product) parent.getAdapter().getItem(position));
                startActivity(intent);
            }
        });
        itemsView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (taskIsRunning)
                    if (totalItemCount > 0 && firstVisibleItem + visibleItemCount == totalItemCount) {
                        Log.i(CATEG, "Need more products.");
                        AppController.getInstance().loadProducts(MainActivity.this);
                        taskIsRunning = true;
                    }
            }
        });

        Log.i(CATEG, "Created...");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(CATEG, "Creating options menu...");

        getMenuInflater().inflate(R.menu.home_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // configure the search info and add any event listeners...

        boolean ret = super.onCreateOptionsMenu(menu);

        Log.i(CATEG, "Options menu created...");

        return ret;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(CATEG, "Option item selected:" + item.getTitle());

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void doSomething(Object o) {
        List<Product> products = (ArrayList<Product>) o;
        this.products.addAll(products);
            adapter.notifyDataSetChanged();
        taskIsRunning = false;
    }
}
