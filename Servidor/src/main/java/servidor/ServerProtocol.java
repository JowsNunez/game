/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import DominioDTO.JugadorDTO;
import DominioDTO.MensajeSockets;
import java.util.List;
import pipesandfilters.AccesoRepo;
import pipesandfilters.IPAF;
import pipesandfilters.IRepo;
import pipesandfilters.commands.InvocadorPln;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

/**
 *
 * @author Equipo 1
 */
public class ServerProtocol {

    private IPAF ipaf;
    private IRepo repo;

    public ServerProtocol() {
        this.ipaf = new InvocadorPln();
        this.repo = new AccesoRepo();
    }

    /**
     *
     * @param mensajeEntrante
     * @return
     */
    public Object processData(Object mensajeEntrante) {

        if (mensajeEntrante instanceof JugadorDTO) {
            System.out.println("[Server/ServerProtocol] " + "Received player: " + mensajeEntrante);
            if (((JugadorDTO) mensajeEntrante).getPuntaje() < 0) {
                return MensajeSockets.REMOVE_PLAYER;
            } else {
                return MensajeSockets.SET_PLAYER;
            }
        } else if (mensajeEntrante instanceof Integer) {
            System.out.println("[Server/ServerProtocol] " + "Received max players: " + mensajeEntrante);
            return MensajeSockets.MAX_PLAYERS;
        } else if (mensajeEntrante == MensajeSockets.MARCADOR) {
            return repo.obtenerMarcador();
        } else if (mensajeEntrante == MensajeSockets.EMPEZAR_PARTIDA) {
            System.out.println("[Server/ServerProtocol] " + "c");
            return MensajeSockets.EMPEZAR_PARTIDA;
        }

        return null;
    }

    public Object empezarPartida(List<JugadorDTO> jugadoresDTO) {
        ipaf.crearSala(jugadoresDTO);
        return repo.obtenerMarcador();
    }
}
