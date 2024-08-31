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
import limchaeyoung.dailySprout.habit.dto.HabitInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "습관", description = "습관 관련 API")
@RestController
@RequestMapping("/habits")
@RequiredArgsConstructor
public class HabitController {
    private final HabitService habitService;

    @Transactional(readOnly = true)
    @GetMapping
    @Operation(summary = "습관 조회",
            description = "활성화된 습관을 조회하는 API입니다.",
            security = @SecurityRequirement(name = SwaggerConfig.JWT_SECURITY_SCHEME))
    public StandardResponse<List<HabitInfoResponse>> getActiveHabits(@AuthenticationPrincipal CustomUserDetails user) {
        return StandardResponse.success(habitService.findAllByUserAndStatus(user.getEmail()));
    }

    @PostMapping("/{habitId}")
    @Operation(summary = "습관 삭제",
            description = "활성화된 습관을 비활성화(삭제)하는 API입니다.",
            security = @SecurityRequirement(name = SwaggerConfig.JWT_SECURITY_SCHEME)
    )
    public StandardResponse<String> deactivateHabit(@PathVariable("habitId") Long habitId, @AuthenticationPrincipal CustomUserDetails user) {
        habitService.deactivateHabit(habitId, user.getEmail());

        return StandardResponse.success("습관 삭제에 성공하였습니다.");
    }

    @PostMapping
    @Operation(summary = "습관 생성",
            description = "습관 생성 API입니다.",
            security = @SecurityRequirement(name = SwaggerConfig.JWT_SECURITY_SCHEME))
    public StandardResponse<String> createHabit(@RequestBody @Valid CreateHabitRequest createHabitRequest, @AuthenticationPrincipal CustomUserDetails user) {
        habitService.createHabit(createHabitRequest, user.getEmail());

        return StandardResponse.success("습관 생성에 성공하였습니다.");
    }
}
