package com.exercise.pactfact.parser;

import android.support.annotation.NonNull;
import android.util.Log;

import com.exercise.pactfact.Define;
import com.exercise.pactfact.model.FactModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Json Data parser for PactFact data
 * </p>
 * Created by aagrwal on 2/06/15.
 */
public class FactParser
{
    private static final String TAG = FactParser.class.getName();
    private static final String BLANK_STR = "";

    /**
     * Method to parse json raw data in to list and returns the Title
     *
     * @param jsonString The raw json data
     * @param dataList the List on which the data will be stored
     * @return Title of the data
     */
    public String parseFactJsonData(@NonNull String jsonString, @NonNull List<FactModel> dataList)
    {
        String title = BLANK_STR;

        if(jsonString == null || jsonString.isEmpty() || dataList == null)
        {
            Log.e(TAG, "Json Data or the list to be populated should not be null");
            return title;
        }

        try
        {
            JSONObject jsonObject = new JSONObject(jsonString);

            if (jsonObject.has(Define.TITLE_TAG))
            {
                title = jsonObject.getString(Define.TITLE_TAG);

                //If there is no title or title is null set it to Blank String
                if (title == null || title.equals("null"))
                {
                    title = BLANK_STR;
                }
            }

            if (jsonObject.has(Define.ROWS_TAG))
            {
                JSONArray rows = jsonObject.getJSONArray(Define.ROWS_TAG);
                parseJsonRows(rows, dataList);
            }

        }
        catch (JSONException e)
        {
            Log.e(TAG, "JSON Exception is caught ### ", e);
        }
        return title;
    }

    /**
     * Method to parse individual rows
     * </p>
     * @param rows JSONArray object of rows
     * @param dataList the list where the rows will be populated
     */
    private void parseJsonRows(@NonNull JSONArray rows, @NonNull List<FactModel> dataList)
    {
        if(dataList == null || rows == null)
        {
            Log.e(TAG, "Input params should not be null");
            return ;
        }

        //clear the data list
        dataList.clear();

        for (int i = 0; i < rows.length(); i++)
        {
            try
            {
                JSONObject rowJSON = rows.getJSONObject(i);

                String rowTitle = getStringFromObject(rowJSON, Define.ROW_TITLE_TAG);
                if (rowTitle == null || rowTitle.equals("null"))
                {
                    rowTitle = BLANK_STR;
                }

                String rowDesc = getStringFromObject(rowJSON, Define.ROW_DESCRIPTION_TAG);
                if (rowDesc == null || rowDesc.equals("null"))
                {
                    rowDesc = BLANK_STR;
                }

                String rowImageHref = getStringFromObject(rowJSON, Define.ROW_IMAGEHREF_TAG);
                if (rowImageHref == null || rowImageHref.equals("null"))
                {
                    rowImageHref = BLANK_STR;
                }

                if(rowTitle.isEmpty() && rowDesc.isEmpty() && rowImageHref.isEmpty())
                {
                    continue; //don't add the row as there is no data in it
                }
                FactModel factModel = new FactModel();

                factModel.setTitle(rowTitle);
                factModel.setDescription(rowDesc);
                factModel.setImageHref(rowImageHref);
                dataList.add(factModel);
            }
            catch (JSONException e)
            {
                Log.e(TAG, "JSON Exception is caught ####", e);
            }
        }
    }

    private String getStringFromObject(JSONObject rowJSON, String element)
    {
        try
        {
            if (rowJSON.has(element))
            {
                return rowJSON.getString(element);
            }

        }
        catch (JSONException e)
        {
            Log.e(TAG, "JSON Exception is caught ####", e);
        }
        return BLANK_STR;
    }
}
