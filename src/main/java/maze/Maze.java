package maze;

import com.rits.cloning.Cloner;
import exception.IncorrectMazeException;

import java.util.List;

/**
 * Created by Dominik Demski on 2015-03-24.
 * Class represents maze.
 */
public class Maze {
    private List<List<Integer>> maze;
    private int[] entrance;
    private Cloner cloner = new Cloner();

    public Maze(){
    }

    public Maze(List<List<Integer>> maze, int[] entrance){
        if(maze != null) {
            this.maze = maze;
        }
       if(entrance != null && entrance.length == 2){
            this.entrance = entrance;
        }
        else throw new IncorrectMazeException();
    }

    /**
     *
     * @return copy of matrix which represents maze.
     */
    public List<List<Integer>> getMaze() {
        return cloner.deepClone(maze);
    }

    /**
     * Set matrix which represents maze.
     * @param maze matrix.
     */
    public void setMaze(List<List<Integer>> maze) {
        this.maze = maze;
    }

    /**
     *
     * @return table which represents entrance (at first index row's coordinate, at second column's).
     */
    public int[] getEntrance() {
        return entrance;
    }

    /**
     * Set table which represents entrance (at first index row's coordinate, at second column's).
     * @param entrance table.
     */
    public void setEntrance(int[] entrance) {
        this.entrance = entrance;
    }

    /**
     * Indicate which type of field is placed at particular coordinates.
     * @param coordinates coordinates to check.
     * @return 0 - if there is corridor at coordinates, 1 if there is wall, otherwise -1.
     */
    public int takeField(Coordinates coordinates){
        int x = coordinates.takeX();
        int y = coordinates.takeY();
        if(x >= 0 && x < maze.size() && y >= 0 && y < maze.get(0).size())
            return maze.get(x).get(y);
        else return -1;
    }

}
