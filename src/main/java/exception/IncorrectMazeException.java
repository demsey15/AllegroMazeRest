package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Dominik Demski on 2015-04-30.
 * Class represents http exception. Throws when given maze is incorrect.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class IncorrectMazeException extends RuntimeException {
    public IncorrectMazeException(){
        super("Przesłany labirynt ma zły format");
    }
}
