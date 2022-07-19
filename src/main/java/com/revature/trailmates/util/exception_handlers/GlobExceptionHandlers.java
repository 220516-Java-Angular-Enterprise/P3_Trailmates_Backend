package com.revature.trailmates.util.exception_handlers;

import com.revature.trailmates.util.custom_exception.AuthenticationException;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import com.revature.trailmates.util.custom_exception.UnauthorizedException;
import com.revature.trailmates.util.enums.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobExceptionHandlers extends ResponseEntityExceptionHandler {

    private final static String status = "Status";
    //region Exception Handlers
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody Map<String, Object> handleUnauthorizedException(UnauthorizedException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put(ExceptionMessage.STATUS.name(), 401);
        responseBody.put(ExceptionMessage.MESSAGE.name(), e.getMessage());
        responseBody.put(ExceptionMessage.TIMESTAMP.name(), LocalDateTime.now().toString());
        return responseBody;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody Map<String, Object> handleAuthenticationException(AuthenticationException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put(ExceptionMessage.STATUS.name(), 403);
        responseBody.put(ExceptionMessage.MESSAGE.name(), e.getMessage());
        responseBody.put(ExceptionMessage.TIMESTAMP.name(), LocalDateTime.now().toString());
        return responseBody;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Map<String, Object> handleInvalidRequestException(InvalidRequestException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put(ExceptionMessage.STATUS.name(), 404);
        responseBody.put(ExceptionMessage.MESSAGE.name(), e.getMessage());
        responseBody.put(ExceptionMessage.TIMESTAMP.name(), LocalDateTime.now().toString());
        return responseBody;
    }

    /**
     * Catches any exceptions in other methods and returns status code 409 if
     * a ResourceConflictException occurs.
     * @param e The resource conflict request being thrown
     * @return A map containing the status code, error message, and timestamp of
     * when the error occurred.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody Map<String, Object> handleResourceConflictException(ResourceConflictException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put(ExceptionMessage.STATUS.name(), 409);
        responseBody.put(ExceptionMessage.MESSAGE.name(), e.getMessage());
        responseBody.put(ExceptionMessage.TIMESTAMP.name(), LocalDateTime.now().toString());
        return responseBody;
    }
    //endregion
}
