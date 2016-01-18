package gois.io.bestbuycatalog.task;

import android.os.AsyncTask;
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

import gois.io.bestbuycatalog.controller.AppController;
import gois.io.bestbuycatalog.model.Product;
import gois.io.bestbuycatalog.util.QueryBuilder;

/**
 * https://api.bestbuy.com/v1/products((categoryPath.id=pcmcat209400050001))?apiKey=sxwj3hhtpnkqex9m95tc7cb5&sort=bestSellingRank.asc&show=bestSellingRank,name,manufacturer,regularPrice,salePrice,image,thumbnailImage,description,longDescription,shortDescription&callback=JSON_CALLBACK&format=json
 * https://api.bestbuy.com/v1/product(categoryPath.id=pcmcat209400050001)?apikey=sxwj3hhtpnkqex9m95tc7cb5&sort=name.asc&show=bestSellingRank,name,manufacturer,regularPrice,salePrice,image,thumbnailImage,description,longDescription,shortDescription&callback=JSON_CALLBACK&format=json
 */
public class GetProductsTask extends AsyncTask<Void, Void, String> {

    private QueryBuilder queryBuilder = null;

    @Override
    protected String doInBackground(Void... params) {
        if (queryBuilder == null) {
            queryBuilder = new QueryBuilder()
                    .url("https://api.bestbuy.com/v1/")
                    .service("products")
                    .searchBy("categoryPath.id=pcmcat209400050001")
                    .key("sxwj3hhtpnkqex9m95tc7cb5");
        }

        queryBuilder.sortBy("bestSellingRank")
                .show("bestSellingRank")
                .show("name")
                .show("manufacturer")
                .show("regularPrice")
                .show("salePrice")
                .show("image")
                .show("thumbnailImage")
                .show("description")
                .show("longDescription")
                .show("shortDescription")
                .jsonFormat(true);

        // http://stackoverflow.com/questions/10500775/parse-json-from-httpurlconnection-object
        HttpURLConnection connection = null;
        try {
            if (!queryBuilder.validate())
                throw new MalformedURLException();
            String query = queryBuilder.build();
            Log.i(toString(), query);
            URL url = new URL(query);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-length", "0");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();

            switch (connection.getResponseCode()) {
                case 200:
                case 201:
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String s;
                    while ((s = reader.readLine()) != null)
                        stringBuilder.append(s + "\n");
                    reader.close();
                    return stringBuilder.substring(0);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            if (s==null)
                Log.i(GetProductsTask.class.getSimpleName(), "null");
            Log.i(GetProductsTask.class.getSimpleName(), s);
            AppController.getInstance().loadProductsfrom(new JSONObject(s.substring(s.indexOf('{'))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
