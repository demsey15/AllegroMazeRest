package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Dominik Demski on 2015-05-03.
 * Class represents http exception. Throws when there is no maze with given id.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IncorrectMazeIDException extends RuntimeException{
    public IncorrectMazeIDException() {
        super("Nie ma labiryntu o podanym id.");
    }
}
