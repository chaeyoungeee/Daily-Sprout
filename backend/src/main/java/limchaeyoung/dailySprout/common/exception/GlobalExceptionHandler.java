package limchaeyoung.dailySprout.common.exception;


import limchaeyoung.dailySprout.achievement.exception.CustomAchievementException;
import limchaeyoung.dailySprout.auth.exception.CustomSecurityException;
import limchaeyoung.dailySprout.auth.exception.CustomWebClientException;
import limchaeyoung.dailySprout.common.response.StandardResponse;
import limchaeyoung.dailySprout.habit.exception.CustomHabitException;
import limchaeyoung.dailySprout.habitDay.exception.CustomHabitDayException;
import limchaeyoung.dailySprout.user.exception.CustomUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static limchaeyoung.dailySprout.common.exception.ErrorCode.INTERNAL_SERVER_ERROR;


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
