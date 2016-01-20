package io.gois.bestbuycatalog.controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.gois.bestbuycatalog.model.Product;
import io.gois.bestbuycatalog.task.GetProductsTask;
import io.gois.bestbuycatalog.task.TaskCallback;

/**
 * Application singleton controller.
 */
public class AppController {

    /**
     * A list of loaded products.
     */
    private List<Product> products;

    /**
     * A object implementing the TaskCallback interface, just a try to simulate callback in JS.
     */
    private TaskCallback callbackObject;

    /**
     * Class constructor.
     */
    private AppController() {
    }

    /**
     * Loads products from BestBuy.com.
     */
    public void loadProducts(TaskCallback callback) {
        log("BEGIN loadProducts");

        callbackObject = callback;
        new GetProductsTask().execute();

        log("END loadProducts");
    }

    /**
     * This method is called in <code>GetProductsTask</code> (after <code>loadProducts</code>
     * call), is where in fact the products are loaded.
     *
     * @param callback the json callback from api call.
     */
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

    private static final String CATEG = "AppController";
    private void log(String message) {
        Log.v(CATEG, message);
    }
}
