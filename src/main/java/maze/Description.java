package maze;

/**
 * Created by Dominik Demski on 2015-04-30.
 * Class represents maze's description (number of walls and corridors) - for rest's needs.
 */
public class Description {
    int walls;
    int corridors;

    public Description(int walls, int corridors) {
        this.walls = walls;
        this.corridors = corridors;
    }


    public int getWalls() {
        return walls;
    }

    public int getCorridors() {
        return corridors;
    }
}
