package br.ufg.inf.es.eir.web;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.ufg.inf.es.eir.model.Remedy;
import okhttp3.Response;

/**
 * Created by marceloquinta on 10/02/17.
 */

public class WebRemedies extends WebConnection {

    private static final String SERVICE = "medicamentos/";

    public WebRemedies() {
        super(SERVICE);
    }

    @Override
    String getRequestContent() {
        Map<String,String> requestMap = new HashMap<>();
        JSONObject json = new JSONObject(requestMap);
        String jsonString = json.toString();

        return  jsonString;
    }

    @Override
    void handleResponse(Response response) {
        String responseBody = null;
        List<Remedy> remedies = new LinkedList<>();
        try {
            responseBody = response.body().string();

            JSONArray jsonArray = new JSONArray(responseBody);
            for(int i=0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Remedy remedy = new Remedy();
                remedy.setName(jsonObject.getString("name"));
                remedies.add(remedy);
            }
            EventBus.getDefault().post(remedies);
        } catch (IOException e) {
            EventBus.getDefault().post(e);
        } catch (JSONException e) {
            EventBus.getDefault().post(new Exception("A resposta do servidor não é válida"));
        }

    }

    @Override
    HttpMethod getMethod() {
        return HttpMethod.GET;
    }
}
