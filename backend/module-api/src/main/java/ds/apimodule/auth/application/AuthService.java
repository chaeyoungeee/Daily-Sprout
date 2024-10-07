package ds.apimodule.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ds.apimodule.auth.dto.KakaoUserInfoResponse;
import ds.apimodule.auth.dto.KakaoUserInfoResponse.KakaoAccount.Profile;
import ds.apimodule.auth.dto.LoginInfoResponse;
import ds.apimodule.auth.dto.LoginResponse;
import ds.apimodule.auth.dto.TokenResponse;
import ds.apimodule.auth.util.jwt.JwtProvider;
import ds.apimodule.user.application.UserService;
import ds.coremodule.user.domain.User;
import ds.coremodule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final KakaoService kakaoService;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public LoginResponse kakaoLogin(final String code) {

        // 카카오 유저 정보 조회
        String kakaoAccessToken = kakaoService.getAccessTokenFromKakao(code);

        KakaoUserInfoResponse userInfo = kakaoService.getUserInfo(kakaoAccessToken);
        Long id = userInfo.getId();
        String email = id + "@kakao.com";
        String nickname = userInfo.getKakaoAccount().getProfile().getNickname();

        // 유저 정보 조회 -> 존재하지 않는다면 가입
        final User user = userRepository.findByEmail(email).orElseGet(() -> createNewKakaoUser(userInfo.getKakaoAccount().getProfile(), email));

        final String accessToken = jwtProvider.issueJwtAccessToken(id, email, nickname);
        final String refreshToken = jwtProvider.issueJwtRefreshToken(id, email, nickname);

        return new LoginResponse(user.getEmail(), user.getNickname(), accessToken, refreshToken);
    }

    private User createNewKakaoUser(final Profile profile, final String email) {
        final User newUser = User.builder()
                .email(email)
                .nickname(profile.nickname)
                .build();
        return userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    public LoginInfoResponse getLoginInfo(final String email) {
        User user = userService.findByEmail(email);

        return new LoginInfoResponse(
                user.getEmail(),
                user.getNickname()
        );
    }

    public TokenResponse reissueToken(final String refreshToken) {
        return jwtProvider.reissueToken(refreshToken);
    }

    public void logout(final String refreshToken) {
        jwtProvider.deleteToken(refreshToken);
    }
}
