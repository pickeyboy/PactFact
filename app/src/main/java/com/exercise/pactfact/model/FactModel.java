package com.exercise.pactfact.model;

/**
 * Model class to store individual row data
 * <p/>
 * Created by aagrwal on 2/06/15.
 */
public class FactModel
{
    private String mDescription;
    private String mTitle;
    private String mImageHref;

    public String getDescription()
    {
        return mDescription;
    }

    public void setDescription(String description)
    {
        mDescription = description;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String title)
    {
        mTitle = title;
    }

    public String getImageHref()
    {
        return mImageHref;
    }

    public void setImageHref(String imageHref)
    {
        mImageHref = imageHref;
    }
}
