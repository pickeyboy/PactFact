package com.exercise.pactfact.model;

/**
 * Model class to store individual row data
 *
 * Created by aagrwal on 2/06/15.
 */
public class FactModel
{
    public String mDescription;

    public String mTitle;

    public String mImageHref;

    public String getDescription()
    {
        return mDescription;
    }

    public void setDescription(String mDescription)
    {
        this.mDescription = mDescription;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String mTitle)
    {
        this.mTitle = mTitle;
    }

    public String getImageHref()
    {
        return mImageHref;
    }

    public void setImageHref(String mImageHref)
    {
        this.mImageHref = mImageHref;
    }
}
