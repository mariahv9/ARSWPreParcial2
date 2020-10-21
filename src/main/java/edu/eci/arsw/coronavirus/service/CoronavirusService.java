package edu.eci.arsw.coronavirus.service;

import edu.eci.arsw.coronavirus.model.CoronavirusStats;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class that implements aplication service and obtains JSON information
 * @author Maria Fernanda Hernandez Vargas
 */
@Service("coronavirus")
public class CoronavirusService {

    @Autowired
    HTTPConnection httpConnection;

    @Autowired
    CoronavirusStats stats;

    /**
     * Method that return JSON information by country name
     * @param name
     * @return
     * @throws CoronavirusException
     */
    public JSONObject getByName (String name) throws CoronavirusException{
        JSONObject jsonObject = stats.getByName(name);
        if (jsonObject != null){
            return jsonObject;
        } else {
            JSONObject object = httpConnection.getName(name);
            stats.addByName(name, object);
            return object;
        }
    }

    /**
     * Method that return JSON of all the information about covid
     * @return
     * @throws CoronavirusException
     */
    public JSONArray getAll () throws CoronavirusException{
        JSONArray jsonArray = stats.getAll();
        if (jsonArray != null){
            return jsonArray;
        } else {
            JSONObject object = httpConnection.getAll();
            stats.addAll(object);
            jsonArray = stats.getAll();
            return jsonArray;
        }
    }

    /**
     * Method that returns JSON country coordinates
     * @param name
     * @return
     * @throws CoronavirusException
     */
    public JSONObject getCoordinatesByName(String name) throws CoronavirusException {
        JSONArray jsonObject = httpConnection.getCoordinates(name);
        int lat = jsonObject.getInt(0);
        int lon = jsonObject.getInt(1);
        JSONObject jsonObject1 = new JSONObject("{\"latitude\":\""+lat+"\",\"longitude\":\""+lon+"\"}");
        return jsonObject1;
    }
}