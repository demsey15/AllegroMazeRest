package maze;

import java.util.List;

/**
 * Created by Dominik Demski on 2015-04-30.
 *
 * Class to validate maze.
 */
public class MazeValidator {
    /**
     * Check if a maze is correct.
     * @param maze maze
     * @return true if maze is correct, otherwise (there is no entrance, maze is not a rectangle,
     * maze is empty (there is any row), maze should consist of only 1 and 0 numbers, entrance must be in maze,
     * entrance must be a corridor field) false.
     */
    public boolean validate(Maze maze){
        int[] entrance = maze.getEntrance();
        if(entrance == null) return false;   //entrance must exist
        List<List<Integer>> m = maze.getMaze();
        if(m.size() < 1) return false; //maze mustn't be empty (it must contain at least one row).

        int rowSize = m.get(0).size();
        for(List<Integer> list : m){            //maze must be a rectangle.
            if(list.size() != rowSize) return false;
            for(Integer i : list){
                if(i != 0 && i != 1) return false;  //maze must consists of 0 and 1 only.
            }
        }

        if(entrance[0] < 0 || entrance[0] >= m.size() || entrance[1] < 0 || entrance[1] >= rowSize) return false;
                                            //entrance must be in maze.
        if(m.get(entrance[0]).get(entrance[1]) != 0) return false; //entrance must be a corridor field.
        return true;
    }
}
