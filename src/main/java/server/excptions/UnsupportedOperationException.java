package server.excptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.NOT_IMPLEMENTED)
public class UnsupportedOperationException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

	    public UnsupportedOperationException() {
	        super();
	    }

	    public UnsupportedOperationException(String message) {
	        super(message);
	    }

	    public UnsupportedOperationException(Throwable cause) {
	        super(cause);
	    }

	    public UnsupportedOperationException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
