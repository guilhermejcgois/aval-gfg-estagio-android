package gois.io.bestbuycatalog.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Credits: http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView image;
    //Map<String, Bitmap> bitmapCache;

    public DownloadImageTask() {
        //bitmapCache = new HashMap<>();
    }

    public DownloadImageTask(ImageView image) {
        this.image = image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        log("BEGIN doInBackground");

        String url = urls[0];
        Bitmap bitmap = null;//bitmapCache.get(url);

        //if (bitmap != null)
            //return bitmap;

        try {
            InputStream in = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
            Log.i("DownloadImageTask", "Image downloaded with success.");
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        log("END doInBackground");

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        log("BEGin doInBackground");

        image.setImageBitmap(bitmap);

        log("END doInBackground");
    }

    private static DownloadImageTask _instance;

    public static DownloadImageTask getInstance(ImageView view) {
        if (_instance == null)
            _instance = new DownloadImageTask();
        _instance.setImage(view);

        return _instance;
    }

    private final String CATEG = "DownloadImageTask";
    private void log(String message) {
        Log.v(CATEG, message);
    }
}
