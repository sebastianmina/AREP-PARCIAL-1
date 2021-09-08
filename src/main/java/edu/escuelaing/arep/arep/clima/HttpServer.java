package edu.escuelaing.arep.arep.clima;

import java.net.*;
import java.nio.charset.Charset;
import java.io.*;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpServer {

    private static final HttpServer _instance = new HttpServer();
    private static final String HTTP_MESSAGE = "HTTP/1.1 200 OK\n" + "Content-Type: #/#\r\n" + "\r\n";
    private static final String WHEATER_QUERY = "https://api.openweathermap.org/data/2.5/weather?q=#&appid=6ff8f8b1dd43268717ea79493222b474";

    public static HttpServer getInstance(){
        return _instance;
    }

    private HttpServer(){}

    /**
     * Inicializa el servicio.
     * @param args
     * @throws IOException
     */
    public void start(String[] args) throws IOException{
        while (true) {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(8080);
			} catch (IOException e) {
				System.err.println("Could not listen on port: 35000.");
				System.exit(1);
			}
			Socket clientSocket = null;
			try {
				System.out.println("Listo para recibir ...");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Failed.");
				System.exit(1);
			}
            try {
                serverConnection(clientSocket);
            } catch (URISyntaxException e) {
                System.err.println("URI incorrect.");
                System.exit(1);
            }
        }
    }

    /**
     * Realiza la conexión del servidor.
     * @param clientSocket
     * @throws IOException
     * @throws URISyntaxException
     */
    public void serverConnection(Socket clientSocket) throws IOException, URISyntaxException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
        String inputLine, outputLine;
        ArrayList<String> request = new ArrayList<String>();

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            request.add(inputLine);
            if (!in.ready()) {
                break;
            }
        }
        String uriStr = request.get(0).split(" ")[1];
        URI resourceURI = new URI(uriStr);
        outputLine = getResource(resourceURI);
        
        out.println(outputLine);
        out.close();
        in.close();
        clientSocket.close();
    }

    /**
     * Clase que toma los recursos.
     * @param resourceURI
     * @return
     * @throws URISyntaxException
     */
    public String getResource(URI resourceURI) throws URISyntaxException{
        String filename = resourceURI.toString().replaceFirst("/", "");
        return computeHTMLResponse(filename);
    }

    /**
     * 
     * @param filename
     * @return
     */
    public String computeHTMLResponse(String filename){
        return "";
    }

    /**
     * Clase que crea un objeto JSON.
     * @param cityname
     * @return
     * @throws IOException
     */
    public static JSONObject WeatherJSON(String cityname) throws IOException{
        InputStream is = new URL(WHEATER_QUERY.replaceFirst("#", cityname)).openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String jsonText = ""; int cp;
        while ((cp = rd.read()) != -1) jsonText += (char) cp;
        JSONObject json = new JSONObject(jsonText);
        return json;
    }
    	/**
	 * Clase encargada de retornar el puerto donde se va dar a ver la pagina
	 * 
	 * @return puerto donde inicia la pagina
	 */
	/*static int getPort() {
		if (System.getenv("PORT") != null) {
			return Integer.parseInt(System.getenv("PORT"));
		}

		return 8080; // returns default port if heroku-port isn't set(i.e. on localhost) }
	}*/
    public static void main(String[] args) throws IOException {
        System.out.println(HttpServer.WeatherJSON("new york"));
    }
}
