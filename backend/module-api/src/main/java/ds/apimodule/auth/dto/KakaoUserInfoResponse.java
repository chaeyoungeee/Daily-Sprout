package ds.apimodule.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true) // 매핑되지 않은 JSON 필드 무시
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // 네이밍 전략 설정 -> 스네이크 케이스
public class KakaoUserInfoResponse {
    public Long id;

    public KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {
        //사용자 프로필
        public Profile profile;

        @Getter
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Profile {
            public String nickname;

//            // 프로필 미리보기 이미지
//            public String thumbnailImageUrl;
//
//            public String profileImageUrl;
//
//            // 기본 프로필인지 여부
//            public String isDefaultImage;
        }
    }

}