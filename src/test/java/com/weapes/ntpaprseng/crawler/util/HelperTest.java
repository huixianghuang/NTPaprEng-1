package com.weapes.ntpaprseng.crawler.util;

import com.weapes.ntpaprseng.crawler.follow.Followable;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by lawrence on 16/8/8.
 */
public class HelperTest {
    @Test
    public void load() throws Exception {
        final List<? extends Followable> load = Helper.load("/Users/lawrence/Documents/practice/Java/NTPaprSEng/conf/allPapersFetch.json");
        Assert.assertTrue(load.size() == 1);
    }

}