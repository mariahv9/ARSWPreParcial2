package edu.eci.arsw.coronavirus.api;

import edu.eci.arsw.coronavirus.service.CoronavirusException;
import edu.eci.arsw.coronavirus.service.CoronavirusService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that implements the aplication controller
 * @author Maria Fernanda Hernandez Vargas
 */
@RestController
@RequestMapping (value = "/coronavirus")
public class CoronavirusController {

    @Autowired
    CoronavirusService coronavirusService;

    /**
     * All information of the api on the path /all
     * @return
     */
    @RequestMapping (path = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(coronavirusService.getAll().toString(), HttpStatus.ACCEPTED);
        } catch (Exception e){
            Logger.getLogger(CoronavirusController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Information by name of the api on the path /{nameCountry}
     * @param name
     * @return
     */
    @RequestMapping (path = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getByName (@PathVariable("name") String name){
        try {
            JSONObject jsonObject = coronavirusService.getByName(name);
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.ACCEPTED);
        } catch (Exception e){
            Logger.getLogger(CoronavirusController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Information about country coordinates
     * @param name
     * @return
     */
    @RequestMapping (path = "/coordinates/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getCoordinates (@PathVariable("name") String name){
        try {
            JSONObject jsonObject = coronavirusService.getCoordinatesByName(name);
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.ACCEPTED);
        } catch (Exception e){
            Logger.getLogger(CoronavirusController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}