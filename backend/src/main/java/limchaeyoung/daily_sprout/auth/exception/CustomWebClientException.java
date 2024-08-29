package limchaeyoung.daily_sprout.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.Charset;

@Getter
public class CustomWebClientException extends WebClientResponseException {
    private final int code;

    public CustomWebClientException(HttpStatus httpStatus, int code, String statusText,
                                    HttpHeaders headers, byte[] body, Charset charset) {
        super(httpStatus.value(), statusText, headers, body, charset);
        this.code = code;
    }
}