package umc.week6.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.week6.global.payload.ApiResponse;
import umc.week6.global.error.dto.ErrorCode;
import umc.week6.global.error.dto.ErrorResponse;

@RestControllerAdvice(annotations = RestController.class)
public class ApiControllerAdvice {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<?> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {

        final ErrorResponse response = ErrorResponse
                .builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .code(e.getMessage())
                .clazz(e.getMethod())
                .message(e.getMessage())
                .build();
        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        ErrorResponse response = ErrorResponse
                .builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .code(e.getMessage())
                .clazz(e.getBindingResult().getObjectName())
                .message(e.toString())
                .fieldErrors(e.getFieldErrors())
                .build();

        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<?> handleInvalidParameterException(InvalidParameterException e) {
        ErrorResponse response = ErrorResponse
                .builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .code(e.getMessage())
                .clazz(e.getErrors().getObjectName())
                .message(e.toString())
                .fieldErrors(e.getFieldErrors())
                .build();

        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(DefaultException.class)
    protected ResponseEntity<?> handleDefaultException(DefaultException e) {

        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse
                .builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(e.toString())
                .build();

        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.resolve(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception e) {

        ErrorResponse response = ErrorResponse
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.toString())
                .build();
        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(DefaultNullPointerException.class)
    protected ResponseEntity<?> handleNullPointerException(DefaultNullPointerException e) {
        ErrorResponse response = ErrorResponse
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();

        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}