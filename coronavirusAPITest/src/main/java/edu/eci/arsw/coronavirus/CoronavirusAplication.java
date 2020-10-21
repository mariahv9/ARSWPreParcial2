package edu.eci.arsw.coronavirus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class that implements the springboot aplication
 * @author Maria Fernanda Hernandez Vargas
 */
@SpringBootApplication
public class CoronavirusAplication {

    /**
     * Method that executes the aplication on port 8080
     * @param args
     */
    public static void main (String[] args) {
        SpringApplication.run (CoronavirusAplication.class, args);
    }
}