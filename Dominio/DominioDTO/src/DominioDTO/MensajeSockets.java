/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominioDTO;

import java.io.Serializable;

/**
 *
 * @author Alejandro Galindo
 */
public enum MensajeSockets implements Serializable {
    REMOVE_PLAYER, UPDATE_PLAYERS, MAX_PLAYERS, CREAR_SALA, EMPEZAR_PARTIDA, SET_PLAYER, TURNO_TERMINADO, MARCADOR
}
