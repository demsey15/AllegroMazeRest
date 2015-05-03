package maze;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Dominik Demski on 2015-03-24.
 * Class responsible for computing different things connected with mazes.
 */
public class MazeService {

    private Maze maze;
    private List<Coordinates> path = null;
    private AtomicInteger numberOfThreads;

    public MazeService(Maze maze){
        this.maze = maze;
    }

    /**
     * Set path from entrance to exit.
     * @param pathFrom path from entrance to exit.
     */
    public void setPathFrom(List<Coordinates> pathFrom) {
        this.path = pathFrom;
    }
    /**
     * Get exit from particular maze (each maze has its determined entrance).
     * @return exit's coordinates for particular maze.
     */
    public Coordinates getExit() {
        Coordinates exit = null;
        int[] entrance = maze.getEntrance();

        int mazeSize = getColumnSize();
        int rowSize = getRowSize();

        int x = entrance[0];
        int y = entrance[1];
        if (x < 0 || x > (mazeSize - 1) || y < 0 || y > (rowSize - 1)) return null;   //this situation shouldn't happen
                                                            // (each maze has been checked before adding to repositoy).
        int i = 0;
                                         //check where is the place on the maze's border with 0 (excluding entrance)
        while(exit == null && i < rowSize) {
            if (maze.getMaze().get(0).get(i) == 0) {
                if (0 != x || i != y) {        //if potential exit doesn't equal entrance
                    exit = new Coordinates(0, i);
                }
            }
            i++;
        }
        i = 0;
        while(exit == null && i < rowSize) {
            if (maze.getMaze().get(mazeSize - 1).get(i) == 0) {
                if ((mazeSize - 1) != x || i != y) {        //if potential exit doesn't equal entrance
                    exit = new Coordinates((mazeSize - 1), i);
                }
            }
            i++;
        }
        i = 1;

        while(exit == null && i < mazeSize - 1) {
            if (maze.getMaze().get(i).get(0) == 0) {
                if (i != x || 0 != y)                   //if potential exit doesn't equal entrance
                    exit = new Coordinates(i, 0);
            }
            i++;
        }
        i = 1;
        while(exit == null && i < mazeSize - 1) {
            if(maze.getMaze().get(i).get(rowSize - 1) == 0) {
                if (i != x || (rowSize - 1) != y)                       //if potential exit doesn't equal entrance
                    return new Coordinates(i, rowSize - 1);
            }
            i++;
        }
        return exit;
    }


    /**
     * Calculate costs of building maze.
     * @param wallCost cost of building a wall's field.
     * @param corridorCost cost of building a corridor's field.
     * @param flambeauPrice cost of building a torch.
     * @return costs of building maze.
     */
    public float getBuildingCost(float wallCost, float corridorCost, float flambeauPrice){
        int [] wallsAndCorridors = countWallsAndCorridors();
        return wallsAndCorridors[0] * wallCost + wallsAndCorridors[1] *
                corridorCost + ((int) (wallsAndCorridors[1] / 2)) * flambeauPrice;
    }

    /**
     * Get path from entrance to exit.
     * @return path from entrance to exit.
     */
    public List<Coordinates> getPath() {
        int[] entrance = maze.getEntrance();
        Coordinates begin = new Coordinates(entrance[0], entrance[1]);
        if (!ifExistsPathFrom()) {                              //if there is no found path from entrance to exit
            numberOfThreads = new AtomicInteger(1);
            (new Thread(new PathFinder(begin, maze, this))).start(); //begin to search a path
            while (!ifExistsPathFrom() && !numberOfThreads.equals(0)) {}
                                                                //wait until path will be found (or there is no path)
        }
        return path;
    }

    public void incrementNumberOfThreads(){
        numberOfThreads.incrementAndGet();
    }
    public void decrementNumberOfThreads(){
        numberOfThreads.decrementAndGet();
    }

    /**
     * calculate number of walls and corridors.
     * @return number of walls and corridors in the table (at 0 index - number of walls, at 1 - number of corridors
     */
    public int[] countWallsAndCorridors(){
            int walls = 0;
            int corridors = 0;
            List<List<Integer>> m = maze.getMaze();
            for (List<Integer> list : m) {
                for (Integer i : list) {
                    if (i == 0) corridors++;
                    else walls++;
                }
            }
        return new int[] {walls, corridors};
        }

    /**
     * Check if there is already found path from entrance to exit.
     * @return true if there is such path, otherwise false.
     */
    public boolean ifExistsPathFrom(){
        return path != null;
    }

    /**
     *
     * @return number of elements in single column in the maze.
     */
    public int getColumnSize(){
        return maze.getMaze().size();
    }

    /**
     *
     * @return number of elements in single row in the maze.
     */
    public int getRowSize(){
        if(maze.getMaze().get(0) == null) return 0;
        return maze.getMaze().get(0).size();
    }
}
