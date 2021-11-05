/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipesandfilters;

import DominioDTO.*;
import java.util.List;

/**
 * El patrón arquitectónico Pipes And Filters es descrito como un patrón
 * estructural, pues detalla la manera en la que distintos objetos se comunican
 * unos con otros, formando consigo estructuras más grandes y/o complejas que
 * puedan implementar nueva funcionalidad, más concretamente, el objetivo de PAF
 * es el de separar el flujo de un proceso en acciones individuales que,
 * usualmente reciben una entrada y generan una salida; Estos son llamados
 * Filters. Los Filters son unidos entre sí por Pipes o vías que solo sirven
 * para dirigir de la manera que sea necesaria, la salida generada por un Filter
 * hacía la entrada de otro, para que al concluir el proceso se tenga una
 * salida, que terminará en un “Sink”, o un objetivo común donde el resultado de
 * todo el proceso puede terminar. Por lo tanto, la arquitectura PAF, apoyada en
 * gran parte del primer principio de SOLID, el “Principio de Responsabilidad
 * Única” restringe al diseño a ser separado según las funcionalidades del
 * sistema, y consecuentemente las responsabilidades de cada función en pasos
 * más pequeños que constituyan a una sola acción, y que estas lleguen a un
 * mismo punto. El uso de sockets en el diseño del sistema involucra además que
 * el diseño arquitectónico sea complejo, y que se vaya acompañado de los demás
 * principios SOLID, sobre todo del quinto, el de “Inversión de dependencias”,
 * donde, al usar sockets, dependemos de abstracciones, no de clases concretas.
 *
 * @author Equipo 1
 */
public interface IPAF {

    void crearSala(List<JugadorDTO> jugadores);

    void asignarLinea(LineaDTO linea);

    void asignarCuadro(CuadroDTO cuadro);

    void retirarJugador(JugadorDTO jugador);

   
}
