package peaksoft.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import peaksoft.exceptions.AuthorizeException;
import peaksoft.exceptions.ForbiddenException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.exceptions.response.ExceptionResponse;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalHandlerException {

    //    404
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFound(NotFoundException notFoundException){
        return ExceptionResponse.builder()
                .exceptionClassName(notFoundException.getClass().getSimpleName())
                .message(notFoundException.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    // 403
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse forbidden(ForbiddenException forbiddenException){
        return ExceptionResponse.builder()
                .exceptionClassName(forbiddenException.getClass().getSimpleName())
                .message(forbiddenException.getMessage())
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badRequest(HttpClientErrorException.BadRequest e){
        return ExceptionResponse.builder()
                .exceptionClassName(e.getClass().getSimpleName())
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badRequest(MethodArgumentNotValidException e){
        return ExceptionResponse.builder()
                .exceptionClassName(e.getClass().getSimpleName())
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(AuthorizeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badRequest(AuthorizeException e){
        return ExceptionResponse.builder()
                .exceptionClassName(e.getClass().getSimpleName())
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badRequest(IllegalArgumentException e){
        return ExceptionResponse.builder()
                .exceptionClassName(e.getClass().getSimpleName())
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badRequest(DateTimeParseException e){
        return ExceptionResponse.builder()
                .exceptionClassName(e.getClass().getSimpleName())
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

}
