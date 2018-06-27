package br.ufg.inf.es.eir.web;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.HttpMethod;

/**
 * Created by marceloquinta on 10/02/17.
 */

public abstract class WebConnection {

    private static final String BASE_URL = "http://private-e681e8-eir1.apiary-mock.com/";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String serviceName;

    public WebConnection(String serviceName){
        this.serviceName = serviceName;
    }

    public void call() {
        OkHttpClient client = new OkHttpClient();

        String url = getUrl();

        RequestBody body = RequestBody.create(JSON, getRequestContent());
//        Request request = new Request.Builder().url(url);
        Request.Builder request = new Request.Builder()
                .url(getUrl());

        switch (getMethod()){
            case POST:
                request.post(body);
                break;
            case PUT:
                request.put(body);
                break;
            case PATCH:
                request.patch(body);
                break;
            case DELETE:
                request.delete(body);
                break;
            case GET:
                request.get();
                break;
        }

        client.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new Exception("Verifique sua conexão"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResponse(response);
            }
        });
    }

    public String getUrl(){
        return BASE_URL + serviceName;
    }

    abstract String getRequestContent();

    abstract HttpMethod getMethod();

    abstract void handleResponse(Response response);

    protected enum HttpMethod {
        GET,POST,PATCH,DELETE,PUT;
    }

}
