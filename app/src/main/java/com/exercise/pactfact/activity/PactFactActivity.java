package com.exercise.pactfact.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.exercise.pactfact.Define;
import com.exercise.pactfact.R;
import com.exercise.pactfact.adapter.FactListAdapter;
import com.exercise.pactfact.model.FactModel;
import com.exercise.pactfact.parser.FactParser;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PactFactActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener
{
    private static final String TAG = PactFactActivity.class.getName();
    private String mActionBarTitle;

    RequestQueue mRequestQueue;

    @InjectView(R.id.fact_list_lv)
    ListView mFactListViewLv;

    @InjectView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.error_message_tv)
    TextView mErrorMessageTv;

    List<FactModel> mFactDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pact_fact);
        ButterKnife.inject(this);

        //Instantiate a new blank list
        mFactDataList = new ArrayList<>();

        mActionBarTitle = getResources().getString(R.string.loading_msg);
        setActionBarTitle(mActionBarTitle);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.pull_to_refresh_background));
        mSwipeRefreshLayout.setColorSchemeColors(Color.WHITE);

        mFactListViewLv.setAdapter(new FactListAdapter(mFactDataList));
        mRequestQueue = AppController.getInstance().getRequestQueue();

        if (!AppController.getInstance().isDataConnAvailable())
        {
            showErrorLayout();
            return;
        }
        loadData();
    }

    @Override
    public void onRefresh()
    {
        mFactDataList.clear();
        ((BaseAdapter) mFactListViewLv.getAdapter()).notifyDataSetChanged();

        if (!AppController.getInstance().isDataConnAvailable())
        {
            showErrorLayout();
            return;
        }

        setActionBarTitle(getResources().getString(R.string.loading_msg));
        AppController.getInstance().getLruBitmapCache().clearCache();
        mRequestQueue.cancelAll(TAG);
        mRequestQueue.getCache().clear();

        loadData();
        mErrorMessageTv.setVisibility(View.INVISIBLE);
    }

    /**
     * Download data from network by adding request in volley request queue
     * and populate data list
     */
    private void loadData()
    {
        final FactParser parser = new FactParser();

        // We first check for cached request
        Cache cache = mRequestQueue.getCache();
        Cache.Entry entry = cache.get(Define.URL_FEED);
        if (entry != null)
        {
            // fetch the data from cache
            try
            {
                String data = new String(entry.data, "UTF-8");
                mActionBarTitle = parser.parseFactJsonData(data, mFactDataList);
                dataLoaded();
            }
            catch (IOException e)
            {
                Log.d(TAG, "Error parsing data", e);
            }
        }
        else
        {
            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    Log.d(TAG, "Volley Response: " + response.toString());
                    if (response != null)
                    {
                        mActionBarTitle = parser.parseFactJsonData(response.toString(), mFactDataList);
                        dataLoaded();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Log.e(TAG, "Error: " + error.getMessage());
                }
            };

            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    Define.URL_FEED, responseListener, errorListener);

            //Setting TAG if in future request needs to be cancelled.
            jsonReq.setTag(TAG);

            // Adding request to volley request queue
            mRequestQueue.add(jsonReq);
        }
    }

    /**
     * Does the operation once data is loaded from network and parsed.
     */
    private void dataLoaded()
    {
        setActionBarTitle(mActionBarTitle);
        ((BaseAdapter) mFactListViewLv.getAdapter()).notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Sets Action bar title.
     * </p>
     *
     * @param title Title String
     */
    private void setActionBarTitle(String title)
    {
        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null && title != null)
        {
            actionBar.setTitle(title);
        }
    }

    /**
     * Display error message layout
     */
    public void showErrorLayout()
    {
        mErrorMessageTv.setVisibility(View.VISIBLE);
        mErrorMessageTv.setText(getResources().getString(R.string.no_internet));
        mSwipeRefreshLayout.setRefreshing(false);
        setActionBarTitle(getResources().getString(R.string.loading_failed));
    }

}
