package maze;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dominik Demski on 2015-04-30.
 * Class responsible for testing adding new maze to rest service.
 */
public class Main {


    public static void main(String[] args){
        RestTemplate restTemplate = new RestTemplate();

        List<List<Integer>> list = new ArrayList<List<Integer>>();
        Integer p1 [] = {1, 1, 1, 0, 1};
        Integer p2 [] = {1, 0, 0, 0, 1};
        Integer p3 [] = {1, 0, 1, 0, 1};
        Integer p4 [] = {1, 0, 1, 1, 1};
        Integer p5 [] = {1, 0, 1, 1, 1};
        Integer p6 [] = {0, 0, 1, 1, 1};
        Integer p7 [] = {1, 1, 1, 1, 1};

        list.add(Arrays.asList(p1));
        list.add(Arrays.asList(p2));
        list.add(Arrays.asList(p3));
        list.add(Arrays.asList(p4));
        list.add(Arrays.asList(p5));
        list.add(Arrays.asList(p6));
        list.add(Arrays.asList(p7));


        Maze maze = new Maze(list, new int[] {0, 3});

        int i = restTemplate.postForObject("http://localhost:8080/maze", maze, Integer.class);
        System.out.println("New id: " + i);

        Maze maze1 = new Maze(list, new int[] {5, 0});
        int j = restTemplate.postForObject("http://localhost:8080/maze", maze1, Integer.class);
        System.out.println("New id: " + j);
    }
}
