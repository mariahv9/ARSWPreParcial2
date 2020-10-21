package edu.eci.arsw.coronavirus.service;

/**
 * Class that implements aplication exception
 * @author Maria Fernanda Hernandez Vargas
 */
public class CoronavirusException extends Exception{
    public static String CONNECTION_FAILED = "Coneccion fallida.";

    /**
     * Method that obtains the message of the exception
     * @param message
     */
    public CoronavirusException(String message){
        super(message);
    }
}
