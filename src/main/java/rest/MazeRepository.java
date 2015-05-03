package rest;

import com.rits.cloning.Cloner;
import exception.IncorrectMazeException;
import exception.IncorrectMazeIDException;
import maze.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dominik Demski on 2015-03-25.
 *
 * Class responsible for storing mazes and providing some features of mazes (such as exit coordinates, path to exit
 * etc.).
 */
public class MazeRepository {
    private List<Maze> mazes = new ArrayList<Maze>();
    private MazeValidator validator = new MazeValidator();
    private Cloner cloner = new Cloner();
    private ConcurrentHashMap<Integer, Path> paths = new ConcurrentHashMap<Integer, Path>();
    private ConcurrentHashMap<Integer, Exit> exits = new ConcurrentHashMap<Integer, Exit>();
    private ConcurrentHashMap<Integer, Description> descriptions = new ConcurrentHashMap<Integer, Description>();

    public MazeRepository(){
        List<List<Integer>> list = new ArrayList<List<Integer>>();  //create repository and add a sample maze
        Integer p1 [] = {1, 1, 1, 1, 1};
        Integer p2 [] = {1, 0, 0, 0, 0};
        Integer p3 [] = {1, 0, 1, 0, 1};
        Integer p4 [] = {1, 0, 1, 1, 1};

        list.add(Arrays.asList(p1));
        list.add(Arrays.asList(p2));
        list.add(Arrays.asList(p3));
        list.add(Arrays.asList(p4));

        Maze maze = new Maze(list, new int[] {3, 1});
        mazes.add(maze);
    }

    /**
     * Add new maze to repository.
     * @param maze maze to add.
     * @return maze's id
     * @throws IncorrectMazeException when maze is incorrect (there is no entrance, maze is not a rectangle,
     * maze is empty (there is any row), maze should consist of only 1 and 0 numbers, entrance must be in maze,
     * entrance must be a corridor field).
     */
    public int addNewMaze(Maze maze){
        if(!validator.validate(maze)) throw new IncorrectMazeException();
        mazes.add(maze);
        return mazes.size() - 1;
    }

    /**
     * Get maze for particular id.
     * @param id id
     * @return Maze with given id (copy).
     * @throws IncorrectMazeIDException when there is no maze with given id (id < 0 || id >= mazes.size()).
     */
    public Maze getMaze(int id){
        if(id < 0 || id >= mazes.size()) throw new IncorrectMazeIDException();
        return cloner.deepClone(mazes.get(id));
    }

    /**
     *
     * @return all mazes in repository (copies).
     */
    public List<Maze> getAllMazes(){
        return cloner.deepClone(mazes);
    }

    /**
     *
     * @param id maze's id
     * @return returns object which describe maze with given id (description consists of number of walls and
     * corridors.
     * @throws IncorrectMazeIDException when there is no maze with given id (id < 0 || id >= mazes.size()).
     */
    public Description describe(int id){
        if(id < 0 || id >= mazes.size()) throw new IncorrectMazeIDException();
        if(descriptions.containsKey(id)) return descriptions.get(id);  //if this description has been already determine
        MazeService mazeService = new MazeService(getMaze(id));
        int[] description = mazeService.countWallsAndCorridors();
        Description descriptionObject = new Description(description[0], description[1]);
        descriptions.put(id, descriptionObject);
        return descriptionObject;
    }

    /**
     *
     * @param id maze's id
     * @return returns object which describe maze's exit (it's coordinates).
     * @throws IncorrectMazeIDException when there is no maze with given id (id < 0 || id >= mazes.size()).
     */
    public Exit getExit(int id){
        if(id < 0 || id >= mazes.size()) throw new IncorrectMazeIDException();
        if(exits.containsKey(id)) return exits.get(id);
        MazeService mazeService = new MazeService(getMaze(id));
        Exit exit =  new Exit(mazeService.getExit());
        exits.put(id, exit);
        return  exit;
    }

    /**
     * Calculate costs of building particular maze.
     * @param id    maze's id.
     * @param wallPrice cost of building a wall's field.
     * @param corridorPrice cost of building a corridor's field.
     * @param torchPrice cost of building a torch.
     * @return costs of building particular maze.
     * @throws IncorrectMazeIDException when there is no maze with given id (id < 0 || id >= mazes.size()).
     */
    public Price getPrice(int id, float wallPrice, float corridorPrice, float torchPrice){
        if(id < 0 || id >= mazes.size()) throw new IncorrectMazeIDException();
        MazeService mazeService = new MazeService(getMaze(id));
        return new Price(mazeService.getBuildingCost(wallPrice, corridorPrice, torchPrice));
    }

    /**
     * Get path from entrance to exit.
     * @param id maze's id.
     * @return path from entrance to exit.
     * @throws IncorrectMazeIDException when there is no maze with given id (id < 0 || id >= mazes.size()).
     */
    public Path getPath(int id){
        if(id < 0 || id >= mazes.size()) throw new IncorrectMazeIDException();
        if(paths.containsKey(id)) return paths.get(id);
        MazeService mazeService = new MazeService(getMaze(id));
        Path path = new Path(mazeService.getPath());
        paths.put(id, path);
        return path;
    }
}
