package ds.coremodule.auth.exception;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.Getter;

@Getter
public class CustomWebClientException extends WebClientResponseException {
    private final int code;

    public CustomWebClientException(HttpStatus httpStatus, int code, String statusText,
                                    HttpHeaders headers, byte[] body, Charset charset) {
        super(httpStatus.value(), statusText, headers, body, charset);
        this.code = code;
    }
}