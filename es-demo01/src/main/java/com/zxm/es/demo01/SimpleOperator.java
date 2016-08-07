package com.zxm.es.demo01;

import com.zxm.es.demo01.bean.Email;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxm on 7/25/16.
 */
public class SimpleOperator {

    private String object2Json(Email email) {
        String json = "";
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("from", email.getFrom())
                    .array("to", email.getTo())
                    .array("cc", email.getCc())
                    .field("title", email.getTitle())
                    .field("content", email.getContent())
                    .field("time", email.getTime())
                    .endObject();
            json = builder.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private List<String> generateData() {
        List<String> emails = new ArrayList<String>();

        emails.add(object2Json(new Email("zhang.san_01@qq.com", "li.si_01@163.com",
                "wang.er_01@gmail.com", "test es email 01", "this is content 01", "2016-07-01")));
        emails.add(object2Json(new Email("zhang.san_02@qq.com", "li.si_02@163.com",
                "wang.er_02@gmail.com", "test es email 02", "this is content 02", "2016-07-02")));
        emails.add(object2Json(new Email("zhang.san_03@qq.com", "li.si_03@163.com",
                "wang.er_03@gmail.com", "test es email 03", "this is content 03", "2016-07-03")));
        emails.add(object2Json(new Email("zhang.san_04@qq.com", "li.si_04@163.com",
                "wang.er_04@gmail.com", "test es email 04", "this is content 04", "2016-07-04")));
        emails.add(object2Json(new Email("zhang.san_05@qq.com", "li.s_05i@163.com",
                "wang.er_05@gmail.com", "test es email 05", "this is content 05", "2016-07-05")));
        emails.add(object2Json(new Email("zhang.san_06@qq.com", "li.s_06i@163.com",
                "wang.er_06@gmail.com", "test es email 06", "this is content 06", "2016-07-06")));
        emails.add(object2Json(new Email("zhang.san_07@qq.com", "li.s_07i@163.com",
                "wang.er_07@gmail.com", "test es email 07", "this is content 07", "2016-07-07")));
        emails.add(object2Json(new Email("zhang.san_08@qq.com", "li.s_08i@163.com",
                "wang.er_08@gmail.com", "test es email 08", "this is content 08", "2016-07-08")));
        emails.add(object2Json(new Email("zhang.san_09@qq.com", "li.s_09i@163.com",
                "wang.er_09@gmail.com", "test es email 09", "this is content 09", "2016-07-09")));
        emails.add(object2Json(new Email("zhang.san_10@qq.com", "li.s_10i@163.com",
                "wang.er_10@gmail.com", "test es email 10", "this is content 10", "2016-07-10")));
        emails.add(object2Json(new Email("zhang.san_11@qq.com", "li.s_11i@163.com",
                "wang.er_11@gmail.com", "test es email 11", "this is content 11", "2016-07-11")));

        return emails;
    }

    private void testInsert(TransportClient client) {
        List<String> emails = generateData();
        int i = 1;
        for(String email : emails) {
            IndexResponse response = client.prepareIndex("email", "test", "" + i++).setSource(email).get();
            if(response.isCreated()) {
                System.out.println("Success insert email: " + email);
            }
        }
    }

    private List<String> testSearch(TransportClient client) {

        List<String> ids = new ArrayList<String>();
        SearchResponse response = client.prepareSearch("email")
                .setTypes("test")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery("from", "zhang.san_01@qq.com"))                 // Query
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
        SearchHits hits = response.getHits();
        for(SearchHit hit : hits) {
            ids.add(hit.getId());
        }

        return ids;
    }

    private boolean testDel(TransportClient client, String id) {
        DeleteResponse response = client.prepareDelete("email", "test", id).get();
        return response.isFound();
    }

    public static void main(String[] args) throws UnknownHostException {

        SimpleOperator operator = new SimpleOperator();

        Settings settings = Settings.settingsBuilder()
                .put("cluster.name","my-application")
                .put("client.transport.sniff", true)
                .build();
        TransportClient client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(
                        InetAddress.getByName("node1"), 9300));


//        operator.testInsert(client);

        List<String> ids = operator.testSearch(client);
        for(String id : ids) {
            System.out.println(id);
            //System.out.println(operator.testDel(client, id));
        }

        client.close();

    }
}
