package edu.escuelaing.arep.arep.clima;

import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class HttpServer {
    /**
    * Clase principal encargada de crear el servidor y el cliente y mostrar los
    * resultados del llamado del archivo
    * 
    * @param args
    * @throws IOException
    */
   public static void main(String[] args) throws IOException {
       
       while (true) {
           ServerSocket serverSocket = null;
           try {
               serverSocket = new ServerSocket(getPort());
           } catch (IOException e) {
               System.err.println("Could not listen on port: 35000.");
               System.exit(1);
           }
           Socket clientSocket = null;
           try {
               System.out.println("Listo para recibir ...");
               clientSocket = serverSocket.accept();
           } catch (IOException e) {
               System.err.println("Accept failed.");
               System.exit(1);
           }

           PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
           BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
   }

   	/**
	 * Clase encargada de retornar el puerto donde se va dar a ver la pagina
	 * 
	 * @return puerto donde inicia la pagina
	 */
	static int getPort() {
		if (System.getenv("PORT") != null) {
			return Integer.parseInt(System.getenv("PORT"));
		}

		return 8080; // returns default port if heroku-port isn't set(i.e. on localhost) }
	}
}
