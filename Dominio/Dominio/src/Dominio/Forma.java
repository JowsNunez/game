/**
 * Forma.java
 */
package Dominio;

/**
 *
 * @author Alejandro Galindo, Francisco Felix
 */
public class Forma {

    private int width;
    private int height;
    private int x;
    private int y;

    public Forma(int width, int height, int x, int y) { //(FormaBuilder builder)
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.width;
        hash = 23 * hash + this.height;
        hash = 23 * hash + this.x;
        hash = 23 * hash + this.y;
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
        final Forma other = (Forma) obj;
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

//    public static class FormaBuilder {
//
//        private int width;
//        private int height;
//        private int x;
//        private int y;
//
//        /**
//         * 
//         * @param width
//         * @param height 
//         */
//        public FormaBuilder(int width, int height) {
//            this.width = width;
//            this.height = height;
//        }
//        
//        /**
//         * 
//         * @param x
//         * @return 
//         */
//        public FormaBuilder x(int x) {
//            this.x = x;
//            return this;
//        }
//        
//        /**
//         * 
//         * @param y
//         * @return 
//         */
//        public FormaBuilder y(int y) {
//            this.y = y;
//            return this;
//        }
//        
//        /**
//         * 
//         * @return 
//         */
//        public Forma build() {
//            Forma forma = new Forma(this);
//            return forma;
//        }
//
//    }

}
