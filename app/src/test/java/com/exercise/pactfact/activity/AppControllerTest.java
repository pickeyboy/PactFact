package com.exercise.pactfact.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import com.exercise.pactfact.BuildConfig;
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
public class AppControllerTest
{
    AppController mAppController;
    Activity mActivity;

    @Before
    public void setUp() throws Exception
    {
        mActivity = Robolectric.buildActivity(PactFactActivity.class).create().resume().get();
        RuntimeEnvironment.application = mActivity.getApplication();
        mAppController = (AppController)mActivity.getApplication();
    }

    @Test
    public void testOnCreateCheckIfAppControllerInstanceIsCreated() throws Exception
    {
        mAppController.onCreate();
        assertThat(AppController.getInstance()).isEqualTo(mAppController);
    }

    @Test
    public void testGetRequestQueueReturnsProperValue() throws Exception
    {
        assertNotNull(mAppController.getRequestQueue());
    }

    @Test
    public void testGetImageLoaderReturnsProperValue() throws Exception
    {
        assertNotNull(mAppController.getImageLoader());
    }

    @Test
    public void testGetLruBitmapCacheReturnsProperValue() throws Exception
    {
        assertNotNull(mAppController.getLruBitmapCache());
    }

    @Test
    public  void testIsDataConnAvailableIfReturnsCorrectValue() throws Exception
    {
        ConnectivityManager cm = (ConnectivityManager)
                RuntimeEnvironment.application.getSystemService(Context.CONNECTIVITY_SERVICE);

        ShadowConnectivityManager shadowCM = Shadows.shadowOf(cm);

        Shadows.shadowOf(shadowCM.getActiveNetworkInfo()).setConnectionStatus(false);
        assertThat(mAppController.isDataConnAvailable()).isFalse();

        Shadows.shadowOf(shadowCM.getActiveNetworkInfo()).setConnectionStatus(true);
        assertThat(mAppController.isDataConnAvailable()).isTrue();
    }
}
