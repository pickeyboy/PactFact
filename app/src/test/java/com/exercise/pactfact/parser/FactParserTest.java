package com.exercise.pactfact.parser;

import com.exercise.pactfact.BuildConfig;
import com.exercise.pactfact.model.FactModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
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
public class FactParserTest
{

    @Test
    public void testParseFactJsonDataNullChecks() throws Exception
    {
        FactParser factParser = new FactParser();
        List<FactModel> factList = null;

        assertThat(factParser.parseFactJsonData(null, null)).isEmpty();
        assertThat(factParser.parseFactJsonData("", null)).isEmpty();

        factList = new ArrayList<>();
        assertThat(factParser.parseFactJsonData("", factList)).isEmpty();
    }

    @Test
    public void testParseFactJsonDataWithDataSetHavingProperValues() throws Exception
    {
        final String DataSet1 = "src/test/assets/data1.json";

        FactParser factParser = new FactParser();
        List<FactModel> factList = new ArrayList<>();
        String title;
        String content;

        content = getContent(DataSet1);
        title = factParser.parseFactJsonData(content, factList);
        assertThat(factList.size()).isEqualTo(2);
        assertNotNull(title);
        assertThat(title.length()).isGreaterThan(0);
    }

    @Test
    public void testParseFactJsonDataWithDataSetHavingTitleAsNull_TitleShouldBeBlankString() throws Exception
    {
        final String DataSet2 = "src/test/assets/data2.json";

        FactParser factParser = new FactParser();
        List<FactModel> factList = new ArrayList<>();
        String title;
        String content;

        content = getContent(DataSet2);
        title = factParser.parseFactJsonData(content, factList);
        assertThat(factList.size()).isEqualTo(2);
        assertNotNull(title);
        assertThat(title.length()).isEqualTo(0);
    }

    @Test
    public void testParseFactJsonDataWithDataSetHavingAllItemOfFirstRowIsNull() throws Exception
    {
        final String DataSet3 = "src/test/assets/data3.json";

        FactParser factParser = new FactParser();
        List<FactModel> factList = new ArrayList<>();
        String title;
        String content;

        content = getContent(DataSet3);
        title = factParser.parseFactJsonData(content, factList);
        assertThat(factList.size()).isEqualTo(1);
        assertNotNull(title);
        assertThat(title.length()).isGreaterThan(0);
    }

    @Test
    public void testParseFactJsonDataWithDataSetHavingDescriptionMissingInOneRow() throws Exception
    {
        final String DataSet4 = "src/test/assets/data4.json";

        FactParser factParser = new FactParser();
        List<FactModel> factList = new ArrayList<>();
        String title;
        String content;

        content = getContent(DataSet4);
        title = factParser.parseFactJsonData(content, factList);
        assertThat(factList.size()).isEqualTo(2);
        assertNotNull(title);
        assertThat(title.length()).isGreaterThan(0);
    }

    @Test
    public void testParseFactJsonDataWithDataSetHavingImageUrlAsNull() throws Exception
    {
        final String DataSet5 = "src/test/assets/data5.json";

        FactParser factParser = new FactParser();
        List<FactModel> factList = new ArrayList<>();
        String title;
        String content;

        content = getContent(DataSet5);
        title = factParser.parseFactJsonData(content, factList);
        assertThat(factList.size()).isEqualTo(2);
        assertNotNull(title);
        assertThat(title.length()).isGreaterThan(0);

    }

    @Test
    public void testParseFactJsonDataWithDataSetWithNoRows() throws Exception
    {
        final String DataSet6 = "src/test/assets/data6.json";

        FactParser factParser = new FactParser();
        List<FactModel> factList = new ArrayList<>();
        String title;
        String content;

        content = getContent(DataSet6);
        title = factParser.parseFactJsonData(content, factList);
        assertThat(factList.size()).isEqualTo(0);
        assertNotNull(title);
        assertThat(title.length()).isGreaterThan(0);
    }

    private String getContent(String filePath)
    {
        File jsonDataFile = new File(filePath);
        assertThat(jsonDataFile).isNotNull();


        String content = null;
        try
        {
            content = new Scanner(jsonDataFile).useDelimiter("\\Z").next();
        }
        catch (FileNotFoundException e)
        {
            fail("Dataset is not found");
        }
        assertThat(content).isNotNull();
        return content;
    }

}
