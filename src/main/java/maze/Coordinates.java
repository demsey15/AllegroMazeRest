package maze;

/**
 * Created by Dominik Demski on 2015-03-24.
 * Class represents coordinates in the maze.
 */
public class Coordinates {
    private int[] entrance = new int[2];  //coordinates named entrance for rest's needs.

    public int[] getEntrance() {
        return entrance;
    }

    public Coordinates(int x, int y){
        entrance[0] = x;
        entrance[1] = y;
    }

    /**
     *
     * @return first part of coordinates.
     */
    public int takeX() {
        return entrance[0];
    }

    /**
     *
     * @return second part of coordinates.
     */
    public int takeY() {
        return entrance[1];
    }



    @Override
    public String toString() {
        return "[" + entrance[0] + ", " + entrance[1] + "]";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null) return false;
        if(this.getClass() != o.getClass()) return false;
        Coordinates o2 = (Coordinates) o;
        return entrance[0] == o2.entrance[0] && entrance[1] == o2.entrance[1];
    }

    public void setEntrance(int[] entrance) {
        this.entrance = entrance;
    }
}
