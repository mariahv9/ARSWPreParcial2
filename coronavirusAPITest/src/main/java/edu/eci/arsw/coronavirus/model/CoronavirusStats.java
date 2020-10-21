package edu.eci.arsw.coronavirus.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that implements the aplication cache
 * @author Maria Fernanda Hernandez Vargas
 */
@Service ("stats")
public class CoronavirusStats {
    private ConcurrentHashMap<String, JSONObject> countries = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, JSONObject> provinces = new ConcurrentHashMap<>();

    /**
     * Method that returns save information about covid by name
     * @param name
     * @return
     */
    public JSONObject getByName (String name){
        if (provinces.containsKey(name)){
            return provinces.get(name);
        }else {
            return null;
        }
    }

    /**
     * Method that returns save information about covid
     * @return
     */
    public JSONArray getAll (){
        String all = "[";
        for (ConcurrentHashMap.Entry<String, JSONObject> in : countries.entrySet()){
            all += in.getValue() + ",";
        }
        all += "{}]";
        if (all.length() > 4){
            JSONArray jsonArray = new JSONArray(all);
            return jsonArray;
        } else {
            return null;
        }
    }

    /**
     * Method that saves the information by name on cache
     * 5 minutes live on cache
     * @param name
     * @param jsonObject
     */
    public void addByName (final String name, JSONObject jsonObject){
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                provinces.remove(name);
                timer.cancel();
            }
        };
        provinces.put(name, jsonObject);
        timer.schedule(timerTask, 300000, 1);
    }

    /**
     * Method that saves all information on cache
     * 5 minutes live on cache
     * @param jsonObject
     */
    public void addAll (JSONObject jsonObject){
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("covid19Stats");
        for (int i = 0; i < jsonArray.length(); i++){
            try{
                JSONObject object = jsonArray.getJSONObject(i);
                if (!countries.containsKey(object.get("country").toString())){
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("country", object.get("country"));
                    jsonObject1.put("deaths", object.get("deaths"));
                    jsonObject1.put("confirmed", object.get("confirmed"));
                    jsonObject1.put("recovered", object.get("recovered"));
                    JSONObject object1 = new JSONObject(jsonObject1.toString());
                    countries.put(object.get("country").toString(), object1);
                } else {
                    JSONObject jsonObject1 = countries.get(object.get("country").toString());
//                    JSONObject jsonObject1 = now.ge;
                    int total = jsonObject1.getInt("deaths") + object.getInt("deaths");
                    jsonObject1.remove("deaths");
                    jsonObject1.put("deaths", total);
                    total = jsonObject1.getInt("confirmed") + object.getInt("confirmed");
                    jsonObject1.remove("confirmed");
                    jsonObject1.put("confirmed", total);
                    int a = jsonObject1.optInt("recovered");
                    int b = object.optInt("recovered");
                    total = a + b;
                    jsonObject1.remove("recovered");
                    jsonObject1.put("recovered", total);
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (ConcurrentHashMap.Entry<String, JSONObject> in : countries.entrySet()){
                    countries.remove(in.getKey());
                }
            }
        };
        timer.schedule(timerTask, 300000, 1);
    }
}