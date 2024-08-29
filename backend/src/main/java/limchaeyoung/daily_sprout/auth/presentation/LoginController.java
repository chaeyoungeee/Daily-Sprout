package limchaeyoung.daily_sprout.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import limchaeyoung.daily_sprout.auth.application.AuthService;
import limchaeyoung.daily_sprout.auth.domain.CustomUserDetails;
import limchaeyoung.daily_sprout.auth.dto.LoginInfo;
import limchaeyoung.daily_sprout.auth.dto.LoginResponse;
import limchaeyoung.daily_sprout.auth.dto.TokenResponse;
import limchaeyoung.daily_sprout.common.response.StandardResponse;
import limchaeyoung.daily_sprout.auth.dto.KakaoLoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인", description = "로그인 관련 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class LoginController {

    private final AuthService authService;
    public static final String REFRESH_TOKEN = "RefreshToken";

    @PostMapping("/login/kakao")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "카카오 로그인 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "카카오 로그인", description = "카카오 로그인 API입니다.")
    public StandardResponse<LoginResponse> kakaoLogin(
             @Valid @RequestBody KakaoLoginRequest kakaoLoginRequest
    ) {
        LoginResponse response = authService.kakaoLogin(kakaoLoginRequest.code());

        log.info("로그인 성공: {}", response.nickname());
        return StandardResponse.success(response);
    }

    @GetMapping("/info")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "유저 정보 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "유저 정보 조회",
            description = "유저 정보 조회 API입니다.",
            security = @SecurityRequirement(name = "JWT Token")  // 이 엔드포인트에만 JWT 인증 요구
    )
    public StandardResponse<LoginInfo> getLoginInfo(@AuthenticationPrincipal CustomUserDetails user) {
//        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LoginInfo response = authService.getLoginInfo(user.getEmail());

        return StandardResponse.success(response);
    }

    @GetMapping("/reissue")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "토큰 재발급 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "토큰 재발급", description = "토큰 재발급 API입니다.")
    public StandardResponse<TokenResponse> reissue(@RequestHeader(REFRESH_TOKEN) String refreshToken) {
        TokenResponse response = authService.reissueToken(refreshToken);
        return StandardResponse.success(response);
    }

    @DeleteMapping("/logout")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그아웃 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "로그아웃", description = "로그아웃 API입니다.")
    public StandardResponse<String> logout(@RequestHeader(REFRESH_TOKEN) String refreshToken) {

        authService.logout(refreshToken);

        return StandardResponse.success("로그아웃 처리되었습니다.");
    }
}
