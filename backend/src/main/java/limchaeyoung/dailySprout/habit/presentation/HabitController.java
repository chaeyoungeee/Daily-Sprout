package limchaeyoung.dailySprout.habit.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import limchaeyoung.dailySprout.auth.domain.CustomUserDetails;
import limchaeyoung.dailySprout.common.exception.ErrorCode;
import limchaeyoung.dailySprout.common.response.StandardResponse;
import limchaeyoung.dailySprout.common.swagger.ApiErrorCodeExample;
import limchaeyoung.dailySprout.common.swagger.ApiErrorCodeExamples;
import limchaeyoung.dailySprout.config.SwaggerConfig;
import limchaeyoung.dailySprout.habit.application.HabitService;
import limchaeyoung.dailySprout.habit.dto.CreateHabitRequest;
import limchaeyoung.dailySprout.habit.dto.HabitInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/habits")
@RequiredArgsConstructor
@Tag(name = "습관", description = "습관 관련 API")
public class HabitController {
    private final HabitService habitService;

    @Transactional(readOnly = true)
    @GetMapping
    @Operation(summary = "습관 조회",
            security = @SecurityRequirement(name = SwaggerConfig.JWT_SECURITY_SCHEME))
    @ApiErrorCodeExamples({ErrorCode.ACCESS_DENIED, ErrorCode.USER_NOT_FOUND})
    public StandardResponse<List<HabitInfoResponse>> getActiveHabits(@AuthenticationPrincipal CustomUserDetails user) {
        return StandardResponse.success(habitService.findAllByUserAndStatus(user.getEmail()));
    }

    @PostMapping("/{habitId}")
    @Operation(summary = "습관 삭제",
            security = @SecurityRequirement(name = SwaggerConfig.JWT_SECURITY_SCHEME)
    )
    public StandardResponse<String> deactivateHabit(
            @Parameter(required = true, description = "습관 아이디")
            @PathVariable("habitId") Long habitId,
            @AuthenticationPrincipal CustomUserDetails user) {
        habitService.deactivateHabit(habitId, user.getEmail());

        return StandardResponse.success("습관 삭제에 성공하였습니다.");
    }

    @PostMapping
    @Operation(summary = "습관 생성",
            security = @SecurityRequirement(name = SwaggerConfig.JWT_SECURITY_SCHEME))
    public StandardResponse<String> createHabit(@RequestBody @Valid CreateHabitRequest createHabitRequest, @AuthenticationPrincipal CustomUserDetails user) {
        habitService.createHabit(createHabitRequest, user.getEmail());

        return StandardResponse.success("습관 생성에 성공하였습니다.");
    }
}
