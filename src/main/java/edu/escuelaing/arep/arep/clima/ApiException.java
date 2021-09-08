package edu.escuelaing.arep.arep.clima;

public class ApiException extends Exception {

    /**
     * Constructor de la clase OpenWeatherCacheException
     * @param msg el mensaje a ser lanzado como excepción
     */
    public ApiException(String msg) {
        super(msg);
    }
}
