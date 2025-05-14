package com.example.true_home.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class RestUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestUtils.class);

    private RestUtils() {
        throw new IllegalStateException("Can not instantiate");
    }

    public static <T> ResponseEntity<RestResponse<T>> successResponse(final T data, final HttpStatus statusCode,
                                                                      final String message) {
        LOGGER.debug("Success   " + message + " statusCode " + statusCode + " Response " + data);
        return new ResponseEntity<RestResponse<T>>(new RestResponse<T>(null, message, data), statusCode);
    }

    public static <T> ResponseEntity<RestResponse<T>> successResponse(final T data, final String message) {
        LOGGER.debug("Success   " + message + " Response " + data);
        return successResponse(data, HttpStatus.OK, message);

    }

    public static <T> ResponseEntity<RestResponse<T>> successResponse(final T data, final HttpStatus statusCode) {
        LOGGER.debug("Success statusCode " + statusCode + " Response " + data);
        return new ResponseEntity<RestResponse<T>>(new RestResponse<T>(data), statusCode);
    }

    public static <T> ResponseEntity<RestResponse<T>> successResponse(final T data) {
        LOGGER.debug("Success Response " + data);
        return successResponse(data, HttpStatus.OK);

    }

    public static <T> ResponseEntity<RestResponse<?>> errorResponse(final String errorMessage, final String errorCode,
                                                                    final HttpStatus statusCode) {
        LOGGER.error("Error " + errorMessage + " errorCode " + errorCode + " statusCode " + statusCode);
        return new ResponseEntity<RestResponse<?>>(new RestResponse<T>(errorCode, errorMessage, null), statusCode);

    }

    public static <T> ResponseEntity<RestResponse<?>> errorResponseWithPayload(final String errorMessage,
                                                                               final String errorCode, final HttpStatus statusCode, T payload) {
        LOGGER.error("Error " + errorMessage + " errorCode " + errorCode + " statusCode " + statusCode);
        return new ResponseEntity<RestResponse<?>>(new RestResponse<T>(errorCode, errorMessage, payload), statusCode);

    }

    public static <T> ResponseEntity<Object> errorResponseWithPayloadObject(final String errorMessage,
                                                                            final String errorCode, final HttpStatus statusCode, T payload) {
        LOGGER.error("Error " + errorMessage + " errorCode " + errorCode + " statusCode " + statusCode);
        return new ResponseEntity<Object>(new RestResponse<T>(errorCode, errorMessage, payload), statusCode);
    }

    public static <T> ResponseEntity<RestResponse<T>> successResponseWithHeaders(final T data, final HttpStatus statusCode,
                                                                                 final String message, final HttpHeaders headers) {
        LOGGER.debug("Success   " + message + " statusCode " + statusCode + " Response " + data);
        return new ResponseEntity<RestResponse<T>>(new RestResponse<T>(null, message, data), headers, statusCode);
    }
}
