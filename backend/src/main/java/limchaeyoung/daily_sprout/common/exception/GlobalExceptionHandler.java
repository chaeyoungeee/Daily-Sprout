package limchaeyoung.daily_sprout.common.exception;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import limchaeyoung.daily_sprout.auth.exception.CustomSecurityException;
import limchaeyoung.daily_sprout.auth.exception.CustomWebClientException;
import limchaeyoung.daily_sprout.common.response.StandardResponse;
import limchaeyoung.daily_sprout.user.exception.CustomUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static limchaeyoung.daily_sprout.common.exception.ErrorCode.INTERNAL_SERVER_ERROR;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomUserException.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<StandardResponse<Void>> handleCustomUserException(CustomUserException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.badRequest().body(StandardResponse.failure(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(CustomWebClientException.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<StandardResponse<Void>> handleCustomWebClientException(CustomWebClientException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.badRequest().body(StandardResponse.failure(e.getCode(), e.getStatusText()));
    }

    @ExceptionHandler(CustomSecurityException.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<StandardResponse<Void>> handleCustomSecurityException(CustomSecurityException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.badRequest().body(StandardResponse.failure(e.getErrorCode().getCode(), e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse<Void>> handleException(Exception e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.internalServerError().body(StandardResponse.failure(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMessage()));
    }
}
