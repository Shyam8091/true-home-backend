package com.example.true_home.exception;

import com.example.true_home.dto.ErrorDetailBO;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.RestUtils;
import com.example.true_home.util.TrueHomeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Exception handler class to handle exceptions thrown from the system.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class trueHomeExceptionHandler extends ResponseEntityExceptionHandler {


    private final Logger LOGGER = LoggerFactory.getLogger(trueHomeExceptionHandler.class);

    /**
     * The exception handler method to handle EsipoValidationException.
     *
     * @param ex         EsipoValidationException
     * @param webRequest
     * @return ResponseEntity<ErrorDetailBO> with the error details
     */
    @ExceptionHandler(trueHomeValidationException.class)
    public final ResponseEntity<ErrorDetailBO> handleEsipoExceptions(trueHomeValidationException ex, WebRequest webRequest) {
        ErrorDetailBO errorDetailBO = new ErrorDetailBO(new Date(), webRequest.getDescription(false));
        errorDetailBO.setCode(TrueHomeConstants.VALIDATION_ERROR_CODE);
        errorDetailBO.setMessage(TrueHomeConstants.VALIDATION_ERROR_MESSAGE);
        errorDetailBO.setValidationErrors(ex.getErrors());
        return new ResponseEntity<ErrorDetailBO>(errorDetailBO, HttpStatus.BAD_REQUEST);
    }

    /**
     * The exception handler method to handle EsipoException.
     *
     * @param ex         EsipoException
     * @param webRequest
     * @return ResponseEntity<ErrorDetailBO> with the error details
     */
    @ExceptionHandler(trueHomeException.class)
    public final ResponseEntity<ErrorDetailBO> handleEsipoExceptions(trueHomeException ex, WebRequest webRequest) {
        ErrorDetailBO errorDetailBO = new ErrorDetailBO(new Date(), webRequest.getDescription(false));
        errorDetailBO.setCode(ex.getCode());
        errorDetailBO.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorDetailBO>(errorDetailBO, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return RestUtils.errorResponseWithPayloadObject("Mandatory fields are not available", "OS_400", HttpStatus.BAD_REQUEST, errors);

    }


    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<Object> handleDataAccessException(
            DataAccessException ex, WebRequest request) {
        LOGGER.info("In DataAccessException { }" + ex.getRootCause().getMessage());
        return RestUtils.errorResponseWithPayloadObject("Issue with Database", "OS_500", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(TrueHomException.class)
    public final ResponseEntity<RestResponse<?>> handleRoadRunrException(TrueHomException exception) {
        return RestUtils.errorResponse(exception.getMessage(), exception.getErrorCode(), HttpStatus.OK);
    }

    /**
     * The exception handler method to handle exceptions thrown from the system. Handles all exceptions except
     * for EsipoValidationException & EsipoException.
     *
     * @param ex         Exception
     * @param webRequest
     * @return ResponseEntity<ErrorDetailBO> with the error details
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetailBO> handleAllExceptions(Exception ex, WebRequest webRequest) {
        ErrorDetailBO errorDetailBO = new ErrorDetailBO(new Date(), webRequest.getDescription(false));
        errorDetailBO.setCode(TrueHomeConstants.GENERAL_EXCEPTION_ERROR_CODE);
        errorDetailBO.setMessage(TrueHomeConstants.GENERAL_EXCEPTION_ERROR_MESSAGE);
        return new ResponseEntity<ErrorDetailBO>(errorDetailBO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

