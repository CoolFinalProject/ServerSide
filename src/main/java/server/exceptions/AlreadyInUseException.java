package server.exceptions;

import java.io.Serial;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AlreadyInUseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AlreadyInUseException() {
        super();
    }

    public AlreadyInUseException(String message) {
        super(message);
    }

    public AlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public AlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }
}
