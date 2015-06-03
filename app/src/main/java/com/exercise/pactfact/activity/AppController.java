package com.exercise.pactfact.activity;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.exercise.pactfact.util.LruBitmapCache;

/**
 * The application class to initialize variables
 * such as request queue, image loaders and cache
 */
public class AppController extends Application
{
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    LruBitmapCache mLruBitmapCache;

    private static AppController mInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance()
    {
        return mInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader()
    {
        getRequestQueue();
        if (mImageLoader == null)
        {
            getLruBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
        }

        return this.mImageLoader;
    }

    public LruBitmapCache getLruBitmapCache()
    {
        if (mLruBitmapCache == null)
            mLruBitmapCache = new LruBitmapCache();
        return this.mLruBitmapCache;
    }

    /**
     * This method checks if data connection is available or not. If available
     * returns true, otherwise returns false.
     *
     * @return boolean True if data connection is available otherwise false
     */
    public boolean isDataConnAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnected())
        {
            //No Internet
            return false;
        }
        return true;
    }
}
