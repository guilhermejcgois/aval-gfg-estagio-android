package gois.io.bestbuycatalog.controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gois.io.bestbuycatalog.model.Product;
import gois.io.bestbuycatalog.task.GetProductsTask;
import gois.io.bestbuycatalog.task.TaskCallback;
import gois.io.bestbuycatalog.util.QueryBuilder;
import gois.io.bestbuycatalog.view.MainActivity;

/**
 * Application singleton controller.
 */
public class AppController {

    /**
     * The list of products.
     */
    private List<Product> products;

    private GetProductsTask productsTask;

    private TaskCallback callbackObject;

    private boolean lockedForRequest = false;

    /**
     * Class constructor.
     */
    private AppController() {
        productsTask = new GetProductsTask();
    }

    /**
     * Returns the list of products.
     * @return the list of products.
     */
    public List<Product> getProducts() {
        return products;
    }

    public boolean isLockedForRequest() {
        return lockedForRequest;
    }

    public void lockedForRequest() {
        this.lockedForRequest = true;
    }

    public void unLockedForRequest() {
        this.lockedForRequest = false;
    }

    /**
     * Loads products from BestBuy.com.
     *
     * @return true if has products loaded, otherwise false.
     */
    public void loadProducts(TaskCallback callback) {
        log("BEGIN loadProducts");

        callbackObject = callback;
        new GetProductsTask().execute();

        log("END loadProducts");
    }

    public void loadProductsFrom(JSONObject callback) {
        log("BEGIN loadProductsFrom");

        if (this.products == null) this.products = new ArrayList<>(); else this.products.clear();

        try {
            JSONArray products = callback.getJSONArray("products");
            for (int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);

                Product p = new Product();
                p.setBrand(product.getString("manufacturer"));
                p.setDescription(product.getString("longDescription"));
                //p.setId(product.getInt("id"));
                p.setName(product.getString("name"));
                p.setPrice(product.getDouble("regularPrice"));
                p.setUrlDetailImage(product.getString("image"));
                p.setUrlItemImage(product.getString("thumbnailImage"));

                this.products.add(p);
            }
            callbackObject.doSomething(this.products);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        log("END loadProductsFrom");
    }

    /**
     * Unique instance of this class.
     */
    private static AppController _instance;

    /**
     * Gets the unique instance of this class.
     *
     * @return the unique instance of this class.
     */
    public static AppController getInstance() {
        if (_instance == null)
            _instance = new AppController();

        return _instance;
    }

    private final String CATEG = "AppController";
    private void log(String message) {
        Log.v(CATEG, message);
    }
}
