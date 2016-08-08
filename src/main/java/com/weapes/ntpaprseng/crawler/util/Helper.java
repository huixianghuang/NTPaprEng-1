package com.weapes.ntpaprseng.crawler.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weapes.ntpaprseng.crawler.follow.AdvSearchLink;
import com.weapes.ntpaprseng.crawler.follow.Followable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.nio.charset.Charset.forName;

/**
 * Created by lawrence on 16/8/7.
 */
public final class Helper {

    private Helper() { }

    private static final OkHttpClient OK_HTTP_CLIENT =
            new OkHttpClient();

    private static final Pattern URL_CHECKER =
            Pattern.compile("\\w+://[\\w.]+/\\S*");

    private static final String BASE_URL =
            "http://nature.com/search";

    public static List<? extends Followable> load(final String filePath)
            throws IOException {
        final JSONObject jsonObject = fileMapToJSONObject(filePath);

        List<Followable> followableList = new ArrayList<>();

        final List<String> urls = parseURLSWithJSONObject(jsonObject);

        for (String url : urls) {
            followableList.add(new AdvSearchLink(url));
        }

        return followableList;
    }

    private static JSONObject fileMapToJSONObject(String file) throws IOException {
        final byte[] bytes =
                Files.readAllBytes(new File(file).toPath());

        return JSON.parseObject(new String(bytes,
                forName("UTF-8")));
    }


    public static String fetchWebPage(final String url)
            throws IOException {

        final Request request = new Request.Builder()
                .url(url)
                .build();

        final Response executed = OK_HTTP_CLIENT.newCall(request)
                .execute();

        return executed.body().string();
    }

    public static boolean isURL(final String url) {
        return URL_CHECKER
                .matcher(url)
                .matches();
    }

    private static List<String> parseURLSWithJSONObject(JSONObject object) {

        final JSONObject range = object.getJSONObject("range");

        final JSONArray categories = object.getJSONArray("categories");

        List<String> urls = new ArrayList<>();

        for (Object category : categories) {
            urls.add(concatUrl(range, category));
        }

        return urls;
    }

    private static String concatUrl(JSONObject range, Object journal) {
        final int begin = range.getInteger("begin");
        final int end = range.getInteger("end");

        return BASE_URL
                + "?data_range=" + begin + "-" + end
                + "&journal=" + journal;

    }
}
