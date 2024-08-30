package limchaeyoung.dailySprout.habit.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import limchaeyoung.dailySprout.auth.domain.CustomUserDetails;
import limchaeyoung.dailySprout.common.response.StandardResponse;
import limchaeyoung.dailySprout.config.SwaggerConfig;
import limchaeyoung.dailySprout.habit.application.HabitService;
import limchaeyoung.dailySprout.habit.dto.CreateHabitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "습관", description = "습관 관련 API")
@RestController
@RequestMapping("/habits")
@RequiredArgsConstructor
public class HabitController {
    private final HabitService habitService;

    @PostMapping
    @Operation(summary = "습관 추가",
            description = "습관 추가 API입니다.",
            security = @SecurityRequirement(name = SwaggerConfig.JWT_SECURITY_SCHEME))
    public StandardResponse<String> createHabit(@RequestBody @Valid CreateHabitRequest createHabitRequest, @AuthenticationPrincipal CustomUserDetails user) {
        habitService.createHabit(createHabitRequest, user.getEmail());

        return StandardResponse.success("습관 추가에 성공하였습니다.");
    }
}
