package com.exercise.pactfact.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;

import com.exercise.pactfact.BuildConfig;
import com.exercise.pactfact.R;
import com.exercise.pactfact.parser.FactParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowConnectivityManager;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test Class for {@link FactParser}
 * </p>
 *
 * Created by aagrwal on 3/06/15.
 */

@Config(sdk = 18,
        manifest = "src/main/AndroidManifest.xml",
        resourceDir = "../../../app/build/intermediates/res/" + BuildConfig.FLAVOR + "/" + BuildConfig.BUILD_TYPE)
@RunWith(RobolectricTestRunner.class)
public class PactFactActivityTest
{
    AppController mAppController;
    PactFactActivity mActivity;

    @Before
    public void setUp() throws Exception
    {
        mActivity = Robolectric.buildActivity(PactFactActivity.class).create().resume().get();
        RuntimeEnvironment.application = mActivity.getApplication();
        mAppController = (AppController)mActivity.getApplication();
    }

    /**
     * This test case checks if Activity is present or not
     */
    @Test
    public void checkActivityNotNull() throws Exception
    {
        assertNotNull(mActivity);
        assertThat(mActivity.mSwipeRefreshLayout).isNotNull();
        assertThat(mActivity.mErrorMessageTv).isNotNull();
        assertThat(mActivity.mFactListViewLv).isNotNull();
    }

    @Test
    public void testOnRefreshErrorLayoutDisplayWhenNoDataConnectivity() throws Exception
    {
        assertEquals(View.INVISIBLE, mActivity.mErrorMessageTv.getVisibility());

        ConnectivityManager cm = (ConnectivityManager)
                RuntimeEnvironment.application.getSystemService(Context.CONNECTIVITY_SERVICE);

        ShadowConnectivityManager shadowCM = Shadows.shadowOf(cm);

        Shadows.shadowOf(shadowCM.getActiveNetworkInfo()).setConnectionStatus(false);
        assertThat(mAppController.isDataConnAvailable()).isFalse();

        mActivity.onRefresh();
        assertEquals(View.VISIBLE, mActivity.mErrorMessageTv.getVisibility());
    }

    @Test
    public void testShowErrorLayoutCheckIfVisibilityOfViewsAreProper() throws Exception
    {
        assertThat(mActivity.mErrorMessageTv.getVisibility()).isEqualTo(View.INVISIBLE);
        mActivity.showErrorLayout();
        assertThat(mActivity.mErrorMessageTv.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(mActivity.mSwipeRefreshLayout.isRefreshing()).isFalse();
        assertThat(mActivity.getSupportActionBar().getTitle()).isEqualTo(mActivity.getResources().getString(R.string.loading_failed));
    }
}
