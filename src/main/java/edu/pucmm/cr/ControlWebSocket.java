package edu.pucmm.cr;

import edu.pucmm.cr.jms.Productor;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@WebSocket
public class ControlWebSocket {

    // Store sessions if you want to, for example, broadcast a message to all users
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

    @OnWebSocketConnect
    public void connected(Session session) {
        System.out.println("Conectando Session: "+session.getLocalAddress().getAddress().toString());
        Main.usuariosConectados.add(session);
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        System.out.println("Desconectando el usuario: "+session.getLocalAddress().getAddress().toString());
        Main.usuariosConectados.remove(session);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("Recibiendo del cliente: "+session.getLocalAddress().getAddress().toString()+" - Mensaje: "+message);
        try {
            new Productor().enviarMensaje(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
