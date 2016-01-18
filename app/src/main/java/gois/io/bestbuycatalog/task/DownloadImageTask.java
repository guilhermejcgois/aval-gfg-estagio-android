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

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        image.setImageBitmap(bitmap);
    }

    private static DownloadImageTask _instance;

    public static DownloadImageTask getInstance(ImageView view) {
        if (_instance == null)
            _instance = new DownloadImageTask();
        _instance.setImage(view);

        return _instance;
    }
}
