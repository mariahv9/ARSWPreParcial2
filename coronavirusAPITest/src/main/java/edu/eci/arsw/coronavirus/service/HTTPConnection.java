package edu.eci.arsw.coronavirus.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Class that implements the connection with the api
 * @author Maria Fernanda Hernandez Vargas
 */
@Service ("conectionAPI")
public class HTTPConnection {

    /**
     * Method that obtains the information by name from API service
     * @param name
     * @return
     * @throws CoronavirusException
     */
    public JSONObject getName (String name) throws CoronavirusException {
        try {
            HttpResponse<String> response = Unirest.get("https://rapidapi.p.rapidapi.com/v1/stats?country=" + name)
                    .header("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
                    .header("x-rapidapi-key", "361b123075msh74943d4e9748c13p1053c6jsn7c3d22d2946a")
                    .asString();
            return new JSONObject(response.getBody());
        }catch (Exception e){
            throw new CoronavirusException(CoronavirusException.CONNECTION_FAILED);
        }
    }

    /**
     * Method that obtains the all information from API service
     * @return
     * @throws CoronavirusException
     */
    public JSONObject getAll () throws CoronavirusException {
        try {
            HttpResponse<String> response = Unirest.get("https://rapidapi.p.rapidapi.com/v1/stats")
                    .header("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
                    .header("x-rapidapi-key", "361b123075msh74943d4e9748c13p1053c6jsn7c3d22d2946a")
                    .asString();
            return new JSONObject(response.getBody());
        } catch (Exception e){
            throw new CoronavirusException(CoronavirusException.CONNECTION_FAILED);
        }
    }

    /**
     * Method that obtains the country coordinates
     * @param name
     * @return
     * @throws CoronavirusException
     */
    public JSONObject getCoordinates(String name) throws CoronavirusException {
        try {
            HttpResponse<String> response = Unirest.get("https://rapidapi.p.rapidapi.com/name/"+name)
                    .header("x-rapidapi-host", "restcountries-v1.p.rapidapi.com")
                    .header("x-rapidapi-key", "361b123075msh74943d4e9748c13p1053c6jsn7c3d22d2946a")
                    .asString();
            return  new JSONObject(response.getBody());
        }catch (Exception e){
            throw new CoronavirusException(CoronavirusException.CONNECTION_FAILED);
        }
    }
}