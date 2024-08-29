package limchaeyoung.daily_sprout.auth.application;

import io.netty.handler.codec.http.HttpHeaderValues;
import limchaeyoung.daily_sprout.auth.dto.KakaoTokenResponse;
import limchaeyoung.daily_sprout.auth.dto.KakaoUserInfoResponse;
import limchaeyoung.daily_sprout.auth.exception.CustomSecurityException;
import limchaeyoung.daily_sprout.auth.exception.CustomWebClientException;
import limchaeyoung.daily_sprout.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KakaoService {
    private static final String KAKAO_TOKEN_PATH = "/oauth/token";
    private static final String KAKAO_USER_PATH = "/v2/user/me";
    private static final String GRANT_TYPE = "authorization_code";

    private final WebClient webClient;
    private final String clientId;
    private final String userInfoUrl;

    public KakaoService(@Value("${oauth.kakao.client-id}") String clientId,
                        @Value("${oauth.kakao.token-url}") String tokenUrl,
                        @Value("${oauth.kakao.user-info-url}") String userInfoUrl,
                        WebClient.Builder webClientBuilder) {
        this.clientId = clientId;
        this.userInfoUrl = userInfoUrl;
        this.webClient = webClientBuilder
                .baseUrl(tokenUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .build();
    }
    public String getAccessTokenFromKakao(final String code) {
        KakaoTokenResponse tokenResponse = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(KAKAO_TOKEN_PATH)
                        .queryParam("grant_type", GRANT_TYPE)
                        .queryParam("client_id", clientId)
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    log.warn("Kakao token Client Error : {}", response.statusCode());
                    ErrorCode errorCode = ErrorCode.KAKAO_CLIENT_ERROR;
                    return Mono.error(new CustomWebClientException(
                            errorCode.getHttpStatus(),
                            errorCode.getCode(),
                            errorCode.getMessage(),
                            null,
                            null,
                            null
                    ));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.warn("Kakao token Server Error : {}", response.statusCode());
                    ErrorCode errorCode = ErrorCode.KAKAO_SERVER_ERROR;
                    return Mono.error(new CustomWebClientException(
                            errorCode.getHttpStatus(),
                            errorCode.getCode(),
                            errorCode.getMessage(),
                            null,
                            null,
                            null
                    ));
                })
                .bodyToMono(KakaoTokenResponse.class)
                .block();

        if (tokenResponse == null) {
            throw new CustomSecurityException(ErrorCode.KAKAO_SERVER_ERROR);
        }
        return tokenResponse.getAccessToken();
    }


    public KakaoUserInfoResponse getUserInfo(final String accessToken) {
        return webClient.mutate()
                .baseUrl(userInfoUrl)
                .build()
                .get()
                .uri(KAKAO_USER_PATH)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    log.warn("Kakao UserInfo Client Error : {}", response.statusCode());
                    ErrorCode errorCode = ErrorCode.KAKAO_CLIENT_ERROR;
                    return Mono.error(new CustomWebClientException(
                            errorCode.getHttpStatus(),
                            errorCode.getCode(),
                            errorCode.getMessage(),
                            null,
                            null,
                            null
                    ));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.warn("Kakao UserInfo Server Error : {}", response.statusCode());
                    ErrorCode errorCode = ErrorCode.KAKAO_SERVER_ERROR;
                    return Mono.error(new CustomWebClientException(
                            errorCode.getHttpStatus(),
                            errorCode.getCode(),
                            errorCode.getMessage(),
                            null,
                            null,
                            null
                    ));
                })
                .bodyToMono(KakaoUserInfoResponse.class)
                .block();
    }
}