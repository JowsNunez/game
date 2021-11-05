/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominioDTO;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Alejandro Galindo
 */
public class JugadorDTO implements Serializable {

    private UUID uniqueID;
    private String nombre;
    private String rutaAvatar;
    private boolean host;
    private int puntaje;
    private String color;
    private boolean ready;

    public JugadorDTO(JugadorDTOBuilder builder) {
        this.uniqueID = builder.uniqueID;
        this.nombre = builder.nombre;
        this.rutaAvatar = builder.rutaAvatar;
        this.host = builder.host;
        this.puntaje = builder.puntaje;
        this.color = builder.color;
        this.ready = builder.ready;
    }

    /**
     *
     * @return
     */
    public boolean isHost() {
        return host;
    }

    /**
     *
     * @return
     */
    public boolean isReady() {
        return ready;
    }

    /**
     *
     * @return
     */
    public UUID getUniqueID() {
        return uniqueID;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return
     */
    public String getRutaAvatar() {
        return rutaAvatar;
    }

    /**
     *
     * @return
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     *
     * @return
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param uniqueID
     */
    public void setUniqueID(UUID uniqueID) {
        this.uniqueID = uniqueID;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @param rutaAvatar
     */
    public void setRutaAvatar(String rutaAvatar) {
        this.rutaAvatar = rutaAvatar;
    }

    /**
     *
     * @param host
     */
    public void setHost(boolean host) {
        this.host = host;
    }

    /**
     *
     * @param puntaje
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    /**
     *
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *
     * @param ready
     */
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.uniqueID);
        hash = 59 * hash + Objects.hashCode(this.nombre);
        hash = 59 * hash + Objects.hashCode(this.rutaAvatar);
        hash = 59 * hash + (this.host ? 1 : 0);
        hash = 59 * hash + this.puntaje;
        hash = 59 * hash + Objects.hashCode(this.color);
        hash = 59 * hash + (this.ready ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JugadorDTO other = (JugadorDTO) obj;
        if (Objects.equals(this.uniqueID, other.uniqueID)) {
            return true;
        }
        if (this.host != other.host) {
            return false;
        }
        if (this.puntaje != other.puntaje) {
            return false;
        }
        if (this.ready != other.ready) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.rutaAvatar, other.rutaAvatar)) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JugadorDTO{" + "uniqueID=" + uniqueID + ", nombre=" + nombre + ", rutaAvatar=" + rutaAvatar + ", host=" + host + ", puntaje=" + puntaje + ", color=" + color + ", ready=" + ready + '}';
    }

    public static class JugadorDTOBuilder {

        private UUID uniqueID;
        private String nombre;
        private String rutaAvatar;
        private String color;
        private boolean host;
        private int puntaje;
        private boolean ready;

        /**
         *
         * @param nombre
         * @param rutaAvatar
         * @param color
         */
        public JugadorDTOBuilder(String nombre, String rutaAvatar, String color) {
            this.uniqueID = UUID.randomUUID();
            this.nombre = nombre;
            this.rutaAvatar = rutaAvatar;
            this.color = color;
            this.puntaje = 0;
            this.host = false;
            this.ready = false;
        }

        /**
         *
         * @param id
         * @return
         */
        public JugadorDTOBuilder id(UUID id) {
            this.uniqueID = id;
            return this;
        }

        /**
         *
         * @param host
         * @return
         */
        public JugadorDTOBuilder host(boolean host) {
            this.host = host;
            return this;
        }

        /**
         *
         * @param puntaje
         * @return
         */
        public JugadorDTOBuilder puntaje(int puntaje) {
            this.puntaje = puntaje;
            return this;
        }

        /**
         *
         * @param ready
         * @return
         */
        public JugadorDTOBuilder ready(boolean ready) {
            this.ready = ready;
            return this;
        }

        /**
         *
         * @return
         */
        public JugadorDTO build() {
            JugadorDTO jugador = new JugadorDTO(this);
            return jugador;
        }

    }

}
