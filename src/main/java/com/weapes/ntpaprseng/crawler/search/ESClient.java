package com.weapes.ntpaprseng.crawler.search;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ESClient {
    private static final String IP = "172.29.108.32";
    private static final int PORT = 9300;
    private static final int NT_PAPER_SIZE = 14;
    private static final int REF_DATA_SIZE = 21;
    private static final String INDEX = "ntpaprseng";
    private static final String NT_PAPER_TYPE = "NT_PAPERS";
    private static final String REF_DATA_TYPE = "REF_DATA";
    private static TransportClient client = null;
    private static ESClient singleton = null;

    private ESClient() {
    }

    public static ESClient getInstance() {
        if (singleton == null) {
            synchronized (ESClient.class) {
                if (singleton == null) {
                    singleton = new ESClient();
                    initClient();
                }
            }
        }
        return singleton;
    }

    private static void initClient() {
        try {
            client = TransportClient.builder().settings(Settings.EMPTY).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(IP), PORT));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public boolean putPaperIntoES(String id, XContentBuilder json) {
        IndexResponse response = client.prepareIndex(INDEX, NT_PAPER_TYPE, id)
                .setSource(json)
                .get();
        return response.isCreated();
    }

    public boolean putMetricsPaperIntoES(XContentBuilder json) {
        IndexResponse response = client.prepareIndex(INDEX, REF_DATA_TYPE)
                .setSource(json)
                .get();
        return response.isCreated();
    }

}
