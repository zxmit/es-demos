package com.zxm.es.load;

import com.alibaba.fastjson.JSON;
import com.zxm.es.load.bean.EMAIL_Test;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.zxm.es.load.bean.EMAIL;
/**
 * Created by zxm on 7/26/16.
 */
public class EmailLoader {

    TransportClient client = null;
    private static final int batch_size = 500;

    private void connect() throws UnknownHostException {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name","es-cluster")
                .put("client.transport.sniff", true)
                .build();
        client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(
                        InetAddress.getByName("node1"), 9300));
    }

    private void start(File file) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        EMAIL email = null;
        String line = "";
        try {
            List<String> jemails = new ArrayList<String>(batch_size);
            int batch = 0;
            while((line = reader.readLine()) != null) {
                jemails.add(line);
                if(++batch == batch_size) {
                    loadBatch(jemails);
                    batch = 0;
                    jemails = new ArrayList<String>(batch_size);
                }
            }
            if(batch != 0) {
                loadBatch(jemails);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void loadBatch(List<String> emails) {

        BulkRequestBuilder bulkRequest = client.prepareBulk();

        for(String email : emails) {
            EMAIL e = JSON.parseObject(email, EMAIL.class);
            long uuid = e.getDATA_UUID();
            bulkRequest.add(client.prepareIndex("xm_email_index_01", "resource", uuid + "")
                            .setSource(email)
            );
        }

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
        }

    }

    public static void main(String[] args) throws FileNotFoundException, UnknownHostException {
        EmailLoader loader = new EmailLoader();
        File file = new File("es-load-data/src/main/resources/simple.json");
        loader.connect();
        loader.start(file);
    }
}
