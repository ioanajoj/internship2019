package Model.Exceptions;

/**
 * @author joj on 4/19/2019
 **/
public class DateTimeParseException extends RuntimeException {
    public DateTimeParseException(String message) {
        super(message);
    }
}
