package np.com.csangharsha.chatsapp.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public GlobalExceptionResponse handleInvalidParameterException(RuntimeException ex) {
        return sendResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public GlobalExceptionResponse handleEmptyParameterException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("%s: %s",fieldError.getField(),fieldError.getDefaultMessage())).collect(Collectors.joining(", "));
        return sendResponse(HttpStatus.UNAUTHORIZED, ex,message);
    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public GlobalExceptionResponse handleBadCredentialException(RuntimeException e){
        return sendResponse(HttpStatus.UNAUTHORIZED,e);
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public GlobalExceptionResponse handleInvalidDataAccessApiUsageException(RuntimeException e){
        return sendResponse(HttpStatus.BAD_REQUEST,e);
    }

    @ExceptionHandler({CustomBackendException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public GlobalExceptionResponse handleCustomBackendException(RuntimeException e){
        return sendResponse(HttpStatus.BAD_REQUEST,e);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public GlobalExceptionResponse handleException(RuntimeException e){
        return sendResponse(HttpStatus.INTERNAL_SERVER_ERROR,e);
    }

    private GlobalExceptionResponse sendResponse(HttpStatus status, Exception ex) {
        return sendResponse(status,ex, ex.getMessage());
    }

    private GlobalExceptionResponse sendResponse(HttpStatus status, Exception ex,String message) {
        return new GlobalExceptionResponse(Instant.now(), status.value(), status.getReasonPhrase(),
                ex.getClass().toString(),message);
    }
}
