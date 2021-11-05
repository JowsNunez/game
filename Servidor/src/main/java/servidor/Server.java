package servidor;

import Dominio.Jugador;
import Pantallas.SalaEspera;
import Sonido.SoundManager;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

/**
 *
 * @author Equipo 1
 */
public class Server {

    static volatile List<ServerThread> threads = new ArrayList<>();

    private int players = 2;
    private Jugador host;
    private SalaEspera salaEspera = SalaEspera.getInstance();
    private SoundManager sound = SoundManager.getInstance();

    private ServerProtocol ssp = new ServerProtocol();
    private Socket socket;
    private ServerSocket serverSocket;

    /**
     *
     * @param players
     */
    public Server(int players) {
        this.players = players;
        startServer(4444);
    }

    /**
     *
     * @return
     */
    public Jugador getHost() {
        return host;
    }

    /**
     *
     * @param host
     */
    public void setHost(Jugador host) {
        this.host = host;
    }

    /**
     *
     * @return
     */
    public int getPlayers() {
        return players;
    }

    /**
     *
     * @param path
     * @return
     */
    private URL setAudioFile(String path) {
        try {
            return new File(path).toURI().toURL();
        } catch (MalformedURLException ex) {
            System.out.println(ansi()
                    .fg(GREEN).a("[Exception] Exception at: ")
                    .fg(RED).a(getClass() + " -> " + Thread.currentThread().getStackTrace()[1].getMethodName())
                    .newline()
                    .a(ex.getMessage()).reset());
        }
        return null;
    }

    /**
     *
     * @throws IOException
     */
    public void addPlayer() throws IOException {
        System.out.println("[Server] " + "New connection incoming from: " + socket.getInetAddress());

        ServerThread serverThread = new ServerThread(socket, threads, players);

        Thread client = new Thread(serverThread);

        threads.add(serverThread);
        System.out.println("[Server] " + threads.size() + " threads currently running.");
        String threadList = "[";
        for (int i = 0; i < threads.size(); i++) {
            threadList += threads.get(i) + ", "; 
        }
        threadList += "]";
        System.out.println("[Server] Threads: " + threadList);

        client.start();
    }

    /**
     *
     * @param port
     */
    public final void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("[Server] " + "\u001B[32m" + "Server initialized at port: " + port + "\u001B[0m");
            System.out.println("[Server] " + "\u001B[32m" + "Allowing for a total of " + "\u001B[34m" + players + " players" + "\u001B[0m");

            Thread server = new Thread() {
                public void run() {
                    while (!serverSocket.isClosed()) {

                        try {
                            socket = serverSocket.accept();

                            if (threads.size() < players) {
                                try {
                                    if (threads.isEmpty()) {
                                        addPlayer();
                                    } else {
                                        if (!salaEspera.isFocused()) {
                                            sound.playSoundEffect(setAudioFile("Audio\\SE\\bell.wav"), sound.getBaseSEVolume());
                                            salaEspera.toFront();
                                        }

                                        int dialogButton = JOptionPane.YES_NO_OPTION;
                                        // como se le podra dar un nombre a una conexion :thonk:
                                        int dialogResult = JOptionPane.showConfirmDialog(null, "Alguien desea unirse a la partida", "Warning", dialogButton);
                                        if (dialogResult == JOptionPane.YES_OPTION) {
                                            addPlayer();
                                        } else {
                                            System.out.println("[Server] " + "Host rejected the connection.");
                                            socket.close();
                                        }
                                    }
                                } catch (IOException ex) {
                                    System.out.println(ansi()
                                            .fg(GREEN).a("[Exception] Exception at: ")
                                            .fg(RED).a(getClass() + " -> " + Thread.currentThread().getStackTrace()[1].getMethodName())
                                            .newline()
                                            .a(ex.getMessage()).reset());
                                }
                            } else {
                                System.out.println("[Server] " + "A new connection was tried to be made but server is full.");
                            }
                        } catch (IOException ex) {
                            System.out.println(ansi()
                                    .fg(GREEN).a("[Exception] Exception at: ")
                                    .fg(RED).a(getClass() + " -> " + Thread.currentThread().getStackTrace()[1].getMethodName())
                                    .newline()
                                    .a(ex.getMessage()).reset());
                        }
                    }
                }
            };
            server.start();

        } catch (IOException ex) {
            System.out.println(ansi()
                    .fg(GREEN).a("[Exception] Exception at: ")
                    .fg(RED).a(getClass() + " -> " + Thread.currentThread().getStackTrace()[1].getMethodName())
                    .newline()
                    .a(ex.getMessage()).reset());
        }
    }
}
