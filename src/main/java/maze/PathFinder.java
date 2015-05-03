package maze;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik Demski on 2015-03-25.
 * Multi-thread class responsible for finding a path from maze's entrance to maze's exit.
 */
public class PathFinder implements Runnable{
    private Coordinates entrance;
    private Maze maze;
    private MazeService mazeService;
    private List<Coordinates> path;
    private Coordinates begin;

    public PathFinder(Coordinates entrance, Maze maze, MazeService mazeService){
        this.entrance = entrance;
        this.maze = maze;
        this.begin = entrance;
        path = new ArrayList<Coordinates>();
        this.mazeService = mazeService;
    }

    public PathFinder(Coordinates entrance, Maze maze, List<Coordinates> path,
                      Coordinates begin, MazeService mazeService){
        this.entrance = entrance;
        this.maze = maze;
        this.path = new ArrayList<Coordinates>(path);
        this.begin = begin;
        this.mazeService = mazeService;
    }

    @Override
    public void run() {
        int currentX = begin.takeX();
        int currentY = begin.takeY();
        int mazeMaxRowNumber = mazeService.getColumnSize() - 1;
        int mazeMaxColumnNumber = mazeService.getRowSize() - 1;
        Coordinates myJob = new Coordinates(currentX, currentY);

        path.add(begin);

        while(!mazeService.ifExistsPathFrom() && myJob != null){
            myJob = null;
            if(currentX - 1 >= 0){
                Coordinates coord = new Coordinates((currentX - 1), currentY);
                if(maze.takeField(coord) == 0){          //if there is no wall here
                    if(!path.contains(coord)){          //if we don't go back
                        myJob = coord;
                    }
                }
            }
            if(currentX + 1 <= mazeMaxRowNumber){
                Coordinates coord = new Coordinates((currentX + 1), currentY);
                if(maze.takeField(coord) == 0){          //if there is no wall here
                    if(!path.contains(coord)){          //if we don't go back
                        if(myJob == null)
                            myJob = coord;
                        else{
                            mazeService.incrementNumberOfThreads();
                            (new Thread(new PathFinder(entrance, maze, path, coord, mazeService))).start();
                        }
                    }
                }
            }
            if(currentY + 1 <= mazeMaxColumnNumber){
                Coordinates coord = new Coordinates(currentX, currentY + 1);
                if(maze.takeField(coord) == 0){          //if there is no wall here
                    if(!path.contains(coord)){          //if we don't go back
                        if(myJob == null)
                            myJob = coord;
                        else{
                            mazeService.incrementNumberOfThreads();
                            (new Thread(new PathFinder(entrance, maze, path, coord, mazeService))).start();
                        }
                    }
                }
            }
            if(currentY - 1 >= 0){
                Coordinates coord = new Coordinates(currentX, currentY - 1);
                if(maze.takeField(coord) == 0){          //if there is no wall here
                    if(!path.contains(coord)){          //if we don't go back
                        if(myJob == null)
                            myJob = coord;
                        else{
                            mazeService.incrementNumberOfThreads();
                            (new Thread(new PathFinder(entrance, maze, path, coord, mazeService))).start();
                        }
                    }
                }
            }
            if(myJob != null){
                currentX = myJob.takeX();
                currentY = myJob.takeY();
                path.add(myJob);
            }
        }
        if(!mazeService.ifExistsPathFrom()){
            Coordinates potentialExit = path.get(path.size() - 1);
            if(potentialExit.equals(mazeService.getExit())){
                mazeService.setPathFrom(path);
            }
        }
        mazeService.decrementNumberOfThreads();
    }
}
