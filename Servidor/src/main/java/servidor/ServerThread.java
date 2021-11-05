/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import DominioDTO.JugadorDTO;
import DominioDTO.MarcadorDTO;
import DominioDTO.MensajeSockets;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

/**
 *
 * @author Equipo 1
 */
public class ServerThread implements Runnable {

    private volatile JugadorDTO jugadorDTO;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Object mensajeEntrante;

    private volatile List<ServerThread> threads;
    private ServerProtocol socketProtocol;
    private Socket socket;

    /**
     *
     * @param socket
     * @param threads
     * @param maxPlayers
     * @throws IOException
     */
    public ServerThread(Socket socket, List<ServerThread> threads, int maxPlayers) throws IOException {

        this.socket = socket;
        this.socketProtocol = new ServerProtocol();

        this.threads = threads;

        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.output.flush();
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    /**
     *
     */
    @Override
    public void run() {

        while (true) {
            try {
                mensajeEntrante = input.readObject();

                Object command = socketProtocol.processData(mensajeEntrante);

                if (command == MensajeSockets.SET_PLAYER) {
                    System.out.println("[Server/ServerThread] " + "Processing player: " + mensajeEntrante);

                    this.jugadorDTO = (JugadorDTO) mensajeEntrante;
                    List<JugadorDTO> jugadores = new ArrayList<>();
                    for (ServerThread thread : threads) {
                        jugadores.add(thread.getJugadorDTO());
                    }

                    if (!jugadores.isEmpty()) {
                        for (int i = 0; i < jugadores.size(); i++) {
                            jugadores.get(i).getNombre();
                            if (jugadores.get(i).equals(this.jugadorDTO)) {
                                System.out.println("[Server/ServerThread] " + "Updating Player...");

                                jugadores.set(i, this.jugadorDTO);
                            }
                        }
                        System.out.println("[Server/ServerThread] " + "Player updated! " + this.jugadorDTO.getNombre() + " (" + this.jugadorDTO.getUniqueID() + ")");
                    }

                    sendToAll(jugadores);

                } else if (command == MensajeSockets.MAX_PLAYERS) {
                    System.out.println("[Server/ServerThread] " + "Processing max players: " + mensajeEntrante);
                    Integer maxPlayers = (Integer) mensajeEntrante;
                    sendToAll(maxPlayers);
                } else if (command == MensajeSockets.EMPEZAR_PARTIDA) {
                    System.out.println("[Server/ServerThread] " + "d");
                    //Object empezarPartida = ssp.empezarPartida(jugadores);
                    //sendToAll(command);
                } else if (command == MensajeSockets.REMOVE_PLAYER) {

                    this.jugadorDTO = (JugadorDTO) mensajeEntrante;
                    List<JugadorDTO> jugadores = new ArrayList<>();
                    for (ServerThread thread : threads) {
                        jugadores.add(thread.getJugadorDTO());
                    }

                    System.out.println("[Server/ServerThread] " + "Removing thread...");

                    for (ServerThread thread : threads) {
                        if (thread.getJugadorDTO().equals(this.jugadorDTO)) {
                            threads.remove(thread);
                            System.out.println("[Server/ServerThread] Thread " + thread + " has been removed");
                            System.out.println("[Server/ServerThread] New thread size: " + threads.size());
                            break;
                        }
                    }

                    System.out.println("[Server/ServerThread] " + "Removing player: " + mensajeEntrante);

                    if (!jugadores.isEmpty()) {
                        for (int i = 0; i < jugadores.size(); i++) {
                            jugadores.get(i).getNombre();
                            if (jugadores.get(i).equals(this.jugadorDTO)) {
                                System.out.println("[Server/ServerThread] " + "Removing Player...");

                                jugadores.set(i, this.jugadorDTO);
                            }
                        }
                        System.out.println("[Server/ServerThread] " + "Player removed! " + this.jugadorDTO.getNombre() + " (" + this.jugadorDTO.getUniqueID() + ")");
                    }

                    sendToAll(jugadores);
                }
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(ansi()
                        .fg(GREEN).a("[Exception] Exception at: ")
                        .fg(RED).a(getClass() + " -> " + Thread.currentThread().getStackTrace()[1].getMethodName())
                        .newline()
                        .a(ex.getMessage()).reset());
                break;
            }
        }
    }

    /**
     *
     * @return
     */
    public JugadorDTO getJugadorDTO() {
        return jugadorDTO;
    }

    /**
     *
     * @return
     */
    public ObjectInputStream getInput() {
        return input;
    }

    /**
     *
     * @return
     */
    public ObjectOutputStream getOutput() {
        return output;
    }

    /**
     *
     * @param mensaje
     */
    public synchronized void sendToSelf(Object mensaje) {
        try {
            this.output.reset();
            this.output.writeObject(mensaje);
            this.output.flush();
        } catch (IOException ex) {
            System.out.println(ansi()
                    .fg(GREEN).a("[Exception] Exception at: ")
                    .fg(RED).a(getClass() + " -> " + Thread.currentThread().getStackTrace()[1].getMethodName())
                    .newline()
                    .a(ex.getMessage()).reset());
        }
    }

    /**
     *
     * @param mensaje
     */
    public synchronized void sendToAll(Object mensaje) {
        System.out.println("[Server/ServerThread] " + "Sending data to all threads...");
        if (!threads.isEmpty()) {
            for (ServerThread thread : threads) {
                try {
                    thread.output.reset();
                    System.out.println("[Server/ServerThread] " + "Sending data to: " + thread);
                    System.out.println("[Server/ServerThread] " + "Data: " + mensaje.getClass() + " " + mensaje);
                    thread.output.writeObject(mensaje);
                    thread.output.flush();
                } catch (IOException ex) {
                    System.out.println(ansi()
                            .fg(GREEN).a("[Exception] Exception at: ")
                            .fg(RED).a(getClass() + " -> " + Thread.currentThread().getStackTrace()[1].getMethodName())
                            .newline()
                            .a(ex.getMessage()).reset());
                }
            }
        }
    }
}
