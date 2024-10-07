package ds.coremodule.exception;


import static ds.coremodule.exception.ErrorCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ds.commonmodule.response.StandardResponse;
import ds.coremodule.achievement.exception.CustomAchievementException;
import ds.coremodule.auth.exception.CustomSecurityException;
import ds.coremodule.auth.exception.CustomWebClientException;
import ds.coremodule.habit.exception.CustomHabitException;
import ds.coremodule.habitDay.exception.CustomHabitDayException;
import ds.coremodule.user.exception.CustomUserException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomUserException.class)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "400", description = "Bad Request")
//    })
    public ResponseEntity<StandardResponse<Void>> handleCustomUserException(CustomUserException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.status(e.getStatus()).body(StandardResponse.failure(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(CustomAchievementException.class)
    public ResponseEntity<StandardResponse<Void>> handleCustomAchievementException(CustomAchievementException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.status(e.getStatus()).body(StandardResponse.failure(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(CustomHabitException.class)
    public ResponseEntity<StandardResponse<Void>> handleCustomHabitException(CustomHabitException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.status(e.getStatus()).body(StandardResponse.failure(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(CustomHabitDayException.class)
    public ResponseEntity<StandardResponse<Void>> handleCustomHabitDayException(CustomHabitDayException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.status(e.getStatus()).body(StandardResponse.failure(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(CustomWebClientException.class)
    public ResponseEntity<StandardResponse<Void>> handleCustomWebClientException(CustomWebClientException e) {

        log.warn(e.getMessage(), e);

        return ResponseEntity.badRequest().body(StandardResponse.failure(e.getCode(), e.getStatusText()));
    }

    @ExceptionHandler(CustomSecurityException.class)
    public ResponseEntity<StandardResponse<Void>> handleCustomSecurityException(CustomSecurityException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.status(e.getStatus()).body(StandardResponse.failure(e.getCode(), e.getMessage()));
    }

    // @Valid exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage(), e);

        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();


        return ResponseEntity.status(e.getStatusCode()).body(StandardResponse.failure(e.getStatusCode().value(), errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse<Void>> handleException(Exception e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.internalServerError().body(StandardResponse.failure(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMessage()));
    }
}
