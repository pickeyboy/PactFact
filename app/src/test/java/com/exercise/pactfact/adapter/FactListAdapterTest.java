package com.exercise.pactfact.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.exercise.pactfact.BuildConfig;
import com.exercise.pactfact.R;
import com.exercise.pactfact.activity.PactFactActivity;
import com.exercise.pactfact.model.FactModel;
import com.exercise.pactfact.parser.FactParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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
public class FactListAdapterTest
{
    FactListAdapter mFactListAdapter;
    List<FactModel> mFactModelList = new ArrayList<>();
    private static final int LIST_SIZE = 5;
    PactFactActivity mActivity;

    @Before
    public void setUp() throws Exception
    {
        mActivity = Robolectric.buildActivity(PactFactActivity.class).create().resume().get();
        for (int i = 0; i < LIST_SIZE; i++)
        {
            FactModel model = mock(FactModel.class);
            mFactModelList.add(model);
        }
        mFactListAdapter = new FactListAdapter(mFactModelList);
    }

    @Test
    public void testIfAdapterCreatedProperly() throws Exception
    {
        assertThat(mFactModelList).isNotEmpty();
        assertThat(mFactListAdapter).isNotNull();
    }

    @Test
    public void testGetCountMethod() throws Exception
    {
        assertThat(mFactListAdapter.getCount()).isEqualTo(LIST_SIZE);
    }

    @Test
    public void testGetItemWithDifferentPositionValue() throws Exception
    {
        assertThat(mFactListAdapter.getItem(-1)).isNull();
        assertThat(mFactListAdapter.getItem(LIST_SIZE+1)).isNull();
        assertThat(mFactListAdapter.getItem(0)).isNotNull();
        assertThat(mFactListAdapter.getItem(1)).isNotNull();
    }

    @Test
    public void testGetItemIdMethod() throws Exception
    {
        assertThat(mFactListAdapter.getItemId(1)).isEqualTo(1);
        assertThat(mFactListAdapter.getItemId(0)).isEqualTo(0);
    }

    @Test
    public void testGetViewCheckIfViewsAreSetProperly() throws Exception
    {
        View converView;
        ViewGroup layout = (ViewGroup)mActivity.getLayoutInflater().inflate(R.layout.activity_pact_fact, null);

        converView = mFactListAdapter.getView(0, null, layout);
        assertThat(converView).isNotNull();

        assertThat(ButterKnife.findById(converView, R.id.fact_row_title_tv)).isNotNull();
        assertThat(ButterKnife.findById(converView, R.id.fact_row_desc_tv)).isNotNull();
        assertThat(ButterKnife.findById(converView, R.id.fact_row_image_iv)).isNotNull();

        converView = mFactListAdapter.getView(1, converView, layout);
        assertThat(converView).isNotNull();

    }
}
