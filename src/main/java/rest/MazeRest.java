package rest;


import maze.Description;
import maze.Exit;
import maze.Maze;
import maze.Price;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Created by Dominik Demski on 2015-03-25.
 * Class responsible for rest's requests.
 */


@RestController
@RequestMapping("/maze")
public class MazeRest {

    private MazeRepository repository = new MazeRepository();


    @RequestMapping(method = RequestMethod.POST)
    public int addNewMaze(@RequestBody Maze maze){
        return repository.addNewMaze(maze);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public int addNewMazePut(@RequestBody Maze maze){
        return repository.addNewMaze(maze);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Maze getMaze(@PathVariable int id){
        return repository.getMaze(id);
    }

    @RequestMapping(value = "/{id}/describe", method = RequestMethod.GET)
    public Description getWallsAndCorridorsNumber(@PathVariable int id){
        return repository.describe(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Maze> getAllMazes(){
        return repository.getAllMazes();
    }

    @RequestMapping(value = "/{id}/exit", method = RequestMethod.GET)
    public Exit getExit(@PathVariable int id){
        return repository.getExit(id);
    }

    @RequestMapping(value = "{id}/quotation", method = RequestMethod.GET)
    public Price getPrice(@RequestParam(value="wallPrice") float wallPrice,
                          @RequestParam(value = "corridorPrice") float corridorPrice,
                        @RequestParam(value = "torchPrice") float torchPrice,
                          @PathVariable int id){
        return repository.getPrice(id, wallPrice, corridorPrice, torchPrice);
    }

    @RequestMapping(value = "{id}/path", method = RequestMethod.GET)
    public maze.Path getPath(@PathVariable int id){
        return repository.getPath(id);
    }


}
