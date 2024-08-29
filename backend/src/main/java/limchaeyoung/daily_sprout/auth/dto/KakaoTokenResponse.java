package limchaeyoung.daily_sprout.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoTokenResponse {
    @JsonProperty("token_type")
    public String tokenType;

    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("id_token")
    public String idToken;

//    @JsonProperty("expires_in")
//    public Integer expiresIn;
//
//    @JsonProperty("refresh_token")
//    public String refreshToken;
//
//    @JsonProperty("refresh_token_expires_in")
//    public Integer refreshTokenExpiresIn;
//
//    @JsonProperty("scope")
//    public String scope;
}