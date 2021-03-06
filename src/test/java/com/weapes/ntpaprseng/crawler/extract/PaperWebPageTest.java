package com.weapes.ntpaprseng.crawler.extract;

import com.alibaba.fastjson.JSON;
import com.weapes.ntpaprseng.crawler.util.Helper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class PaperWebPageTest {
    @Test
    @Ignore
    public void extract() throws Exception {
        final String url = "http://www.nature.com/nature/journal/v535/n7610/full/nature18590.html";
        final String html = Helper.fetchWebPage(url);
        final PaperWebPage paperWebPage =
                new PaperWebPage(html, url);
        final String jsonString = JSON.toJSONString(paperWebPage.extract());
        System.out.println(jsonString);
        Assert.assertNotNull(jsonString);
    }

}