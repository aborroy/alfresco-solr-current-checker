package org.alfresco.rest.client.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class SolrClient {

    @Value("${solr.server.url}")
    String url;

    OkHttpClient client;

    @PostConstruct
    public void init() {
        client = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
    }

    public boolean isCurrent() throws IOException {
        Request request = new Request.Builder()
                .url(url + "/alfresco/admin/luke?_=" + System.currentTimeMillis() + "&numTerms=0&show=index&wt=json")
                .get()
                .build();
        Call call = client.newCall(request);
        String response = call.execute().body().string();
        JsonNode json = new ObjectMapper().readTree(response);
        return json.get("index").get("current").asBoolean();
    }

}
