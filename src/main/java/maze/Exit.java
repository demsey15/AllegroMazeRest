package maze;

/**
 * Created by Dominik Demski on 2015-04-30.
 * Class represents exit (for rest's needs).
 */
public class Exit {
    private Coordinates exit;

    public Exit(Coordinates exit) {
        this.exit = exit;
    }


    public int[] getExit() {
        return new int[] {exit.takeX(), exit.takeY()};
    }
}
