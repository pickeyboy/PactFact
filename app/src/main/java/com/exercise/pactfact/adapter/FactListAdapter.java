package com.exercise.pactfact.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.exercise.pactfact.R;
import com.exercise.pactfact.activity.AppController;
import com.exercise.pactfact.model.FactModel;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Adapter class for ListView
 * </p>
 * Created by aagrwal on 3/06/15.
 */
public class FactListAdapter extends BaseAdapter
{
    List<FactModel> mFactModelList;
    ListItemViewHolder mListItemViewHolder;

    public FactListAdapter(List<FactModel> list)
    {
        mFactModelList = list;
    }

    @Override
    public int getCount()
    {
        return mFactModelList.size();
    }

    @Override
    public Object getItem(int position)
    {
        if(position < 0 || position > mFactModelList.size())
        {
            return null;
        }
        return mFactModelList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.fact_item, parent, false);

        }

        mListItemViewHolder = (ListItemViewHolder) convertView.getTag();
        if(mListItemViewHolder == null)
        {
            mListItemViewHolder = new ListItemViewHolder();
            mListItemViewHolder.rowTitle = ButterKnife.findById(convertView, R.id.fact_row_title_tv);
            mListItemViewHolder.rowDescription = ButterKnife.findById(convertView, R.id.fact_row_desc_tv);
            mListItemViewHolder.rowImage = ButterKnife.findById(convertView, R.id.fact_row_image_iv);
            convertView.setTag(mListItemViewHolder);
        }

        mListItemViewHolder.rowTitle.setText(mFactModelList.get(position).getTitle());
        mListItemViewHolder.rowDescription.setText(mFactModelList.get(position).getDescription());
        mListItemViewHolder.rowImage.setImageUrl(mFactModelList.get(position).getImageHref(),
                AppController.getInstance().getImageLoader());

        return convertView;
    }

    public class ListItemViewHolder
    {
        public TextView rowTitle;
        public TextView rowDescription;
        public NetworkImageView rowImage;
    }

}
