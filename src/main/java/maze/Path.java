package maze;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik Demski on 2015-04-30.
 * Class which represents path in a maze (for rest service's needs).
 */
public class Path {
    private List<List<Integer>> path = new ArrayList<List<Integer>>();

    public Path(List<Coordinates> path) {
        for(Coordinates coordinates : path){
            List<Integer> coord = new ArrayList<Integer>(2);
            coord.add(coordinates.takeX());
            coord.add(coordinates.takeY());
            this.path.add(coord);
        }
    }


    public List<List<Integer>> getPath() {
        return path;
    }
}
