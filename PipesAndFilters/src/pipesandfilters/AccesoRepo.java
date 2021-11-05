/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipesandfilters;

import Dominio.Cuadro;
import Dominio.Jugador;
import Dominio.Linea;
import Dominio.Marcador;
import DominioDTO.CuadroDTO;
import DominioDTO.JugadorDTO;
import DominioDTO.LineaDTO;
import DominioDTO.MarcadorDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandro Galindo
 */
public class AccesoRepo implements IRepo {

    private SinkRepo sr = SinkRepo.getInstance();

    /**
     * 
     * @return 
     */
    @Override
    public MarcadorDTO obtenerMarcador() {
        Marcador marcador = sr.obtenerMarcador();

        List<JugadorDTO> jugadoresDTO = new ArrayList<>();
        for (Jugador jugador : marcador.getJugadores()) {
            jugadoresDTO.add(new JugadorDTO.JugadorDTOBuilder(jugador.getNombre(), jugador.getRutaAvatar(), jugador.getColor())
                    .id(jugador.getUniqueID())
                    .host(jugador.isHost())
                    .puntaje(jugador.getPuntaje())
                    .ready(jugador.isReady())
                    .build());
        }
        MarcadorDTO marcadorDTO = new MarcadorDTO(jugadoresDTO);

        return marcadorDTO;
    }

    /**
     * 
     * @return 
     */
    @Override
    public LineaDTO getLastLine() {
        Linea linea = sr.obtenerUltimaLinea();
        if (linea != null) {
            LineaDTO lineaDTO
                    = new LineaDTO(
                            linea.getPosicion().toString(),
                            linea.getIndice(),
                            new JugadorDTO.JugadorDTOBuilder(
                                    linea.getJugador().getNombre(),
                                    linea.getJugador().getRutaAvatar(),
                                    linea.getJugador().getColor())
                                    .id(linea.getJugador().getUniqueID())
                                    .host(linea.getJugador().isHost())
                                    .puntaje(linea.getJugador().getPuntaje())
                                    .ready(linea.getJugador().isReady())
                                    .build());
            return lineaDTO;
        } else {
            return null;
        }
    }

    /**
     * 
     * @return 
     */
    @Override
    public CuadroDTO obtenerUltimoCuadro() {
        Cuadro cuadro = sr.obtenerUltimoCuadro();
        CuadroDTO cuadroDTO
                = new CuadroDTO(cuadro.getIndice(),
                        new JugadorDTO.JugadorDTOBuilder(
                                    cuadro.getJugador().getNombre(),
                                    cuadro.getJugador().getRutaAvatar(),
                                    cuadro.getJugador().getColor())
                                    .id(cuadro.getJugador().getUniqueID())
                                    .host(cuadro.getJugador().isHost())
                                    .puntaje(cuadro.getJugador().getPuntaje())
                                    .ready(cuadro.getJugador().isReady())
                                    .build());
        return cuadroDTO;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int getNextTurn() {
        return sr.obtenerTurnoSiguiente();
    }
}
