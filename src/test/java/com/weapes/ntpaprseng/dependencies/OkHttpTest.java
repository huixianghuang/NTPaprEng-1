package com.weapes.ntpaprseng.dependencies;

import com.weapes.ntpaprseng.crawler.util.Helper;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by lawrence on 16/8/7.
 */
public class OkHttpTest {

    @Test
    @Ignore
    public void testNatureAdvance() throws IOException {

        final String htmlMsg = Helper.fetchWebPage("http://www.nature.com/search/advanced");
        assertNotNull(htmlMsg);

    }

}
