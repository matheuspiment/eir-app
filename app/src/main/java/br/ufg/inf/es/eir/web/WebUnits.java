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
import br.ufg.inf.es.eir.model.Unit;
import okhttp3.Response;

/**
 * Created by marceloquinta on 10/02/17.
 */

public class WebUnits extends WebConnection {

    private static final String SERVICE = "unidades/";

    public WebUnits() {
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
        List<Unit> units = new LinkedList<>();
        try {
            responseBody = response.body().string();

            JSONArray jsonArray = new JSONArray(responseBody);
            JSONObject unitAddress;
            JSONArray remediesFromJSON;
            ArrayList remedies;

            for(int i=0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Unit unit = new Unit();

                unit.setId(jsonObject.getInt("id"));
                unit.setName(jsonObject.getString("name"));
                unit.setImage(jsonObject.getString("img"));

                unitAddress = jsonObject.getJSONObject("address");
                unit.setStreet(unitAddress.getString("street"));
                unit.setNumber(unitAddress.getInt("number"));
                unit.setComplement(unitAddress.getString("complement"));
                unit.setRegion(unitAddress.getString("region"));
                unit.setZipcode(unitAddress.getString("zipcode"));
                unit.setCity(unitAddress.getString("city"));
                unit.setState(unitAddress.getString("state"));
                unit.setCountry(unitAddress.getString("country"));

                unit.setPhone(jsonObject.getString("phone"));

                remediesFromJSON = new JSONArray(jsonObject.getJSONArray("remedies").toString());
                remedies = new ArrayList();

                for (int j = 0; j < remediesFromJSON.length(); j++) {
                    JSONObject remedyJSONObject = remediesFromJSON.getJSONObject(j);
                    remedies.add(remedyJSONObject.getInt("id"));
                }

                unit.setRemedies(remedies);

                units.add(unit);
            }
            EventBus.getDefault().post(units);
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
