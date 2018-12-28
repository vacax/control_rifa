package edu.pucmm.cr;

import edu.pucmm.cr.jms.Consumidor;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.webSocket;

public class Main {

    //Creando el repositorio de las sesiones recibidas.
    public static List<Session> usuariosConectados = new ArrayList<>();
    public static int puerto = 4567;

    public static void main(String[] args) throws JMSException {
        System.out.println("Iniciando APP Control RIFA");
        if(args.length >= 1){
           puerto = Integer.parseInt(args[0]);
        }
        System.out.println("Inicializando en el puerto: "+puerto);
        Spark.port(puerto);
        //
        new Consumidor().conectar();
        //
        webSocket("/ws", ControlWebSocket.class);
        //
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return render(model, "index.ftl");
        });
    }

    /**
     * Permite enviar un mensaje al cliente.
     * @param mensaje
     */
    public static void enviarMensajeAClientesConectados(String mensaje){
        for(Session sesionConectada : usuariosConectados){
            try {
                sesionConectada.getRemote().sendString(mensaje);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @param model
     * @param templatePath
     * @return
     */
    public static String render(Map<String, Object> model, String templatePath) {
        return new FreeMarkerEngine().render(new ModelAndView(model, templatePath));
    }




}
